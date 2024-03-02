package com.redis.sample;

import com.redis.sample.config.RedisStartLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * @description: redis服务启动类
 * @auther: echo
 * @date: 2023/3/29
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(value = {"com.base.common.*","com.redis.sample.*"})
public class RedisApplication {

    /**
     * redis 启动命令类
     */
    @Autowired
    private RedisStartLineRunner redisStartLineRunner;

    /**
     * main 方法
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    /**
     * redis启动
     * @throws Exception 例外
     */
    @PostConstruct
    public void redisStart() throws Exception {
        redisStartLineRunner.run(null);
    }

}
