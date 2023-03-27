package com.datasource.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @author: echo
 * @date: 2023/3/16
 */
@Configuration
public class DruidAutoConfiguration {
        @Autowired
        DynamicDataSourceProvider dynamicDataSourceProvider;

        @Bean
        DynamicDataSource dynamicDataSource() {
            return new DynamicDataSource(dynamicDataSourceProvider);
        }
}
