package com.kafka.sample.dml;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;

import java.util.Properties;

/**
 * @description: kafka dml操作示例
 * @author: echo
 * @date: 2022/7/6
 */
public class KafkaTopicDML {

    public static void main(String[] args) {
        //创建kafkaAdminClient
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"CentOS101:9092,CentOS102:9092,CentOS103:9092");
        KafkaAdminClient adminClient = (KafkaAdminClient) KafkaAdminClient.create(props);

        adminClient.close();
    }

}
