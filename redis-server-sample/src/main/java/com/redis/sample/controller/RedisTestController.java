package com.redis.sample.controller;


import com.base.common.vo.BaseQuery;
import com.base.common.vo.ResponseVO;
import com.redis.sample.entity.User;
import com.redis.sample.server.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * redis测试控制器
 *
 * @author echo
 * @date 2024/01/25
 */
@RestController
@RequestMapping("/redisTest")
public class RedisTestController {

    /**
     * redis服务
     */
    @Autowired
    private RedisService redisService;

    @PostMapping("/set")
    ResponseVO set(String key,String value){
        redisService.set(key, value);
        return ResponseVO.success();
    }

    @PostMapping("/get")
    ResponseVO get(String key){
        return ResponseVO.success().data(redisService.get(key));
    }

    @PostMapping("/hGet")
    ResponseVO hGet(String fKey, String sKey){
        User user1 = new User("1", "tom", 14);
        User user2 = new User("2", "tom1", 17);
        redisService.hSet("user","1", Arrays.asList(user1, user2));
        return ResponseVO.success().data(redisService.hGet(fKey, sKey,User.class));
    }


    @PostMapping("/queryByPage")
    ResponseVO queryByPage(@RequestBody BaseQuery baseQuery){
        redisService.clearAll();
        User user1 = new User("1", "tom", 14);
        User user2 = new User("2", "jerry", 24);
        User user3 = new User("3", "kitty", 53);
        User user4 = new User("4", "mickey", 21);
        User user5 = new User("5", "java", 30);
        User user6 = new User("6", "c++", 35);
        User user7 = new User("7", "python", 46);
        User user8 = new User("8", "golang", 55);
        User user9 = new User("9", "C#", 32);
        User user10 = new User("10", ".net", 11);

        redisService.definedHSet(user1.getId(), user1);
        redisService.definedHSet(user2.getId(), user2);
        redisService.definedHSet(user3.getId(), user3);
        redisService.definedHSet(user4.getId(), user4);
        redisService.definedHSet(user5.getId(), user5);
        redisService.definedHSet(user6.getId(), user6);
        redisService.definedHSet(user7.getId(), user7);
        redisService.definedHSet(user8.getId(), user8);
        redisService.definedHSet(user9.getId(), user9);
        redisService.definedHSet(user10.getId(), user10);

        Long start = Long.valueOf((baseQuery.getCurrentPage()-1)*baseQuery.getPageSize());
        Long end = Long.valueOf(baseQuery.getCurrentPage()* baseQuery.getPageSize());
        List<Object> sort = redisService.sort("ids:user","user:id:*->age",start , end);
        return ResponseVO.success().data(sort);
    }

}
