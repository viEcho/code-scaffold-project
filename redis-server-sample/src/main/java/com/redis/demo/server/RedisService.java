package com.redis.demo.server;


import com.base.common.enums.ResponseCodeEnum;
import com.base.common.global.CommonConstant;
import com.base.common.global.GlobalException;
import com.base.common.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

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


    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

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
        redisTemplate.opsForValue().set(key,null==value?"":value.toString());
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
    public void set(String key,@Nullable Object value,Long expireTime){
        ExceptionUtil.assertNotBlank(key, "key must not be blank!");
        redisTemplate.opsForValue().set(key,null==value?"":value.toString(),
                expireTime == null? CommonConstant.R_KEY_DEFAULT_EXPIRE_TIME:expireTime,
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
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * @description: 加锁
     * @author: echo
     * @date: 2022/4/2
     * @param: lockKey
     * @return: java.lang.Boolean
     */
    public Lock addLock(String lockKey, int expireTime){
        RLock lock;
        ExceptionUtil.assertNotBlank(lockKey, "lockKey must not be blank!");
        try {
            lock = redissonClient.getLock(lockKey);
            lock.lock(expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RedisService addLock fail:",e);
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
    public Boolean addLockWithTime(String lockKey,Long leaseTime,Long waitTimeout){
        ExceptionUtil.assertNotBlank(lockKey, "lockKey must not be blank!");
        try {
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(waitTimeout==null?DEFAULT_LOCK_WAIT_TIME:waitTimeout,
                    leaseTime==null?DEFAULT_LOCK_RELEASE_TIME:leaseTime,
                    TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RedisService addLockWithTime fail:",e);
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
    public void releaseLock(String lockKey){
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
    public Boolean ifExistKey(String key){
        ExceptionUtil.assertNotBlank(key, "key must not be blank!");
        return redisTemplate.hasKey(key);
    }
}
