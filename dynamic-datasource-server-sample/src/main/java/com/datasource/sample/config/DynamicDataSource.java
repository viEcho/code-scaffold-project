package com.datasource.sample.config;

import com.datasource.sample.utils.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: TODO
 * @author: echo
 * @date: 2023/3/16
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    DynamicDataSourceProvider dynamicDataSourceProvider;

    public DynamicDataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
        this.dynamicDataSourceProvider = dynamicDataSourceProvider;
        Map<Object, Object> targetDataSources = new HashMap<>(dynamicDataSourceProvider.loadDataSources());
        super.setTargetDataSources(targetDataSources);
        super.setDefaultTargetDataSource(dynamicDataSourceProvider.loadDataSources().get(DynamicDataSourceProvider.DEFAULT_DATASOURCE));
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceType = DynamicDataSourceContextHolder.getDataSourceType();
        return dataSourceType;
    }
}
