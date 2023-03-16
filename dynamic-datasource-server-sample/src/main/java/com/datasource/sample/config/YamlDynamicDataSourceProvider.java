package com.datasource.sample.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: 数据源配置类
 * @author: echo
 * @date: 2023/3/16
 */
@Configuration
@EnableConfigurationProperties(DruidProperties.class)
public class YamlDynamicDataSourceProvider implements DynamicDataSourceProvider {
    @Autowired
    DruidProperties druidProperties;

    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSource> dataSourceMap = new HashMap<>(druidProperties.getDs().size());
        try {
            Map<String, Map<String, String>> map = druidProperties.getDs();
            Set<String> keySet = map.keySet();
            for (String s : keySet) {
                DruidDataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(map.get(s));
                dataSourceMap.put(s, druidProperties.dataSource(dataSource));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSourceMap;
    }
}
