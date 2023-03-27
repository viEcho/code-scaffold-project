package com.datasource.sample.config;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @description: 动态数据源提供者
 * @author: echo
 * @date: 2023/3/16
 */
public interface DynamicDataSourceProvider {
    String DEFAULT_DATASOURCE = "master";
    /**
     * 加载所有的数据源Da
     * @return
     */
    Map<String, DataSource> loadDataSources();
}
