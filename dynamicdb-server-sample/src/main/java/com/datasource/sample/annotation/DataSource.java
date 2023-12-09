package com.datasource.sample.annotation;

import com.datasource.sample.config.DynamicDataSourceProvider;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 数据源
 * @author: echo
 * @date: 2023/3/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DataSource {
    String dataSourceName() default DynamicDataSourceProvider.DEFAULT_DATASOURCE;

    @AliasFor("dataSourceName")
    String value() default DynamicDataSourceProvider.DEFAULT_DATASOURCE;
}
