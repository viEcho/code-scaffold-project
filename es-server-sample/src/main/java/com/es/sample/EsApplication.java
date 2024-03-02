package com.es.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: es服务启动类
 * @author: echo
 * @date: 2023/3/29
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(value = {"com.base.common.*","com.es.sample.*"})
public class EsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class,args);
    }
}
