package com.es.sample.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * es配置
 *
 * @author echo
 * @date 2024/01/27
 */
@Configuration
public class EsConfig extends AbstractElasticsearchConfiguration {

    /**
     * 弹性搜索客户端
     *
     * @return {@link RestHighLevelClient}
     */
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
