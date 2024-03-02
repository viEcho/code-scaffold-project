package com.redis.sample.server;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.base.common.enums.ResponseCodeEnum;
import com.base.common.constant.CommonConstant;
import com.base.common.global.GlobalException;
import com.base.common.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @description: redis 服务
 * 注意这里set get都需要使用stringRedisTemplate
 * 因为涉及到redis的存取序列化的问题，stringRedisTemplate 和jdk 序列化的方式类似
 * @author: echo
 * @date: 2022/3/10
 */
@Service
@Slf4j
public class RedisService {
    /**
     * 默认一直持有锁
     */
    private final static Long DEFAULT_LOCK_RELEASE_TIME = -1L;

    /**
     * 默认等待锁5秒
     */
    private final static Long DEFAULT_LOCK_WAIT_TIME = 5L;


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    public ValueOperations<String, String> string;
    public HashOperations<String, String, String> hash;
    public ListOperations<String, String> list;
    public SetOperations<String, String> set;
    public ZSetOperations<String, String> zSet;

    @Autowired
    public RedisService(StringRedisTemplate redisTemplate) {
        string = redisTemplate.opsForValue();
        hash = redisTemplate.opsForHash();
        list = redisTemplate.opsForList();
        set = redisTemplate.opsForSet();
        zSet = redisTemplate.opsForZSet();
    }

    public void clearAll() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushDb();
            return "clear all";
        });
    }

    public <T> void hSet(String fKey, String sKey, T entity) {
        hash.put(fKey, sKey, JSONUtil.toJsonStr(entity));
    }

    public <T> List<T> hGet(String fKey, String sKey, Class<T> clazz) {
        String dataStr = hash.get(fKey, sKey);
        if (dataStr.contains("{") && !dataStr.contains("[")) {
            return Arrays.asList(JSONUtil.toBean(dataStr, clazz));
        } else if (dataStr.contains("[")) {
            JSONArray objects = JSONUtil.parseArray(dataStr);
            return JSONUtil.toList(objects, clazz);
        }
        return null;
    }

    /**
     * 按Hash添加实体
     */
    public <T> void definedHSet(String id, T entity) {
        Class<?> tClass = entity.getClass();
        Field[] fields = tClass.getDeclaredFields();
        String classLowerName = tClass.getSimpleName().toLowerCase();
        for (Field f : fields) {
            String name = f.getName();
            String getter = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Object returnType = null;
            try {
                returnType = tClass.getMethod(getter).invoke(entity);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            if (returnType != null) {
                hash.put(classLowerName + ":id:" + id, name, returnType.toString());
            }
        }
        list.leftPush("ids:" + classLowerName, id);

    }

    /**
     * 分页查询,降序
     *
     * @param key        一般是id列表
     * @param by_pattern 根据此pattern的value排序，除了常规的pattern，也可以接收hash的pattern
     * @param offset     偏移量
     * @param count      每次查询的条数
     * @return 返回分页后的id列表
     */
    public List<Object> sort(String key, String by_pattern, Long offset, Long count) {
        List<Object> sort = redisTemplate.sort(
                SortQueryBuilder
                        .sort(key)
                        .by(by_pattern)
                        .alphabetical(true)
                        .order(SortParameters.Order.DESC)
                        .limit(offset, count)
                        .build(),
                RedisSerializer.json()
        );
        return sort;
//        List<Object> returnList = new ArrayList<>();
//        sort.forEach(k -> {
//            List<Object> values = redisTemplate.opsForHash().values(k.toString());
//            returnList.addAll(values);
//        });
//        return returnList;
    }

    /**
     * @description: set值
     * @author: echo
     * @date: 2022/4/2
     * @param: key
     * @param: value
     * @return: void
     */
    public void set(String key, @Nullable Object value) {
        ExceptionUtil.assertNotBlank(key, "key must not be blank!");
        redisTemplate.opsForValue().set(key, null == value ? "" : value);
    }

    /**
     * @description: set值 并设置过期时间 默认时间单位为秒
     * 注意这里必须加上默认时间类型，否则set值后get会乱码，具体可比较有第四个参数和无第四个参数方法的差异
     * @author: echo
     * @date: 2022/4/10
     * @param: key
     * @param: value
     * @param: expireTime
     * @return: void
     */
    public void set(String key, @Nullable Object value, Long expireTime) {
        ExceptionUtil.assertNotBlank(key, "key must not be blank!");
        redisTemplate.opsForValue().set(key, null == value ? "" : value,
                expireTime == null ? CommonConstant.R_KEY_EXPIRE_TIME_DEFAULT : expireTime,
                TimeUnit.SECONDS);
    }

    /**
     * @description: get值
     * @author: echo
     * @date: 2022/4/2
     * @param: key
     * @return: java.lang.Object
     */
    public Object get(String key) {
        ExceptionUtil.assertNotBlank(key, "key must not be blank!");
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @description: 加锁
     * @author: echo
     * @date: 2022/4/2
     * @param: lockKey
     * @return: java.lang.Boolean
     */
    public Lock addLock(String lockKey, int expireTime) {
        RLock lock;
        ExceptionUtil.assertNotBlank(lockKey, "lockKey must not be blank!");
        try {
            lock = redissonClient.getLock(lockKey);
            lock.lock(expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RedisService addLock fail:", e);
            throw new GlobalException(ResponseCodeEnum.ADD_REDIS_LOCK_FAIL);
        }
        return lock;
    }

    /**
     * @description: 加锁并设置获取锁的等待锁的超时时间 及持有锁的时间 默认时间为秒
     * 注意这里必须加上默认时间类型，否则set值后get会乱码，具体可比较有第三个参数和无第三个参数方法的差异
     * @author: echo
     * @date: 2022/4/2
     * @param: lockKey
     * @param: leaseTime 持有锁时间
     * @param: waitTimeout 等待锁时间
     * @return: java.lang.Boolean
     */
    public Boolean addLockWithTime(String lockKey, Long leaseTime, Long waitTimeout) {
        ExceptionUtil.assertNotBlank(lockKey, "lockKey must not be blank!");
        try {
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(waitTimeout == null ? DEFAULT_LOCK_WAIT_TIME : waitTimeout,
                    leaseTime == null ? DEFAULT_LOCK_RELEASE_TIME : leaseTime,
                    TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RedisService addLockWithTime fail:", e);
        }
        return false;
    }

    /**
     * @description: 释放锁
     * @author: echo
     * @date: 2022/4/2
     * @param: lockKey
     * @return: java.lang.Boolean
     */
    public void releaseLock(String lockKey) {
        ExceptionUtil.assertNotBlank(lockKey, "lockKey must not be blank!");
        RLock lock = redissonClient.getLock(lockKey);
        //防止线程不持有锁的情况下，在finally调用unlock释放锁，报IllegalMonitorStateException错，增加判断
        // 是否还是锁定状态
        if (lock.isLocked()) {
            // 是否为当前执行线程持有的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * @description: 判断redis中key 是否存在
     * @author: echo
     * @date: 2022/4/8
     * @param: key
     * @return: java.lang.Boolean
     */
    public Boolean ifExistKey(String key) {
        ExceptionUtil.assertNotBlank(key, "key must not be blank!");
        return redisTemplate.hasKey(key);
    }
}
