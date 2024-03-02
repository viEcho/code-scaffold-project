package com.datasource.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 动态数据源服务启动类
 * @author: echo
 * @date: 2023/3/16
 */
@SpringBootApplication
@ComponentScan(value = {"com.base.common.*","com.datasource.sample.*"})
@MapperScan("com.datasource.sample.mapper")
public class DbServerSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbServerSampleApplication.class,args);
    }
}
