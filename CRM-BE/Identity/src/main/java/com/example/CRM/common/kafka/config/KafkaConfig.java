package com.example.CRM.common.kafka.config;

import com.example.CRM.common.kafka.util.TopicUtil;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    NewTopic notification() {
        // topic name, partition numbers, replication number
        return new NewTopic(TopicUtil.NOTIFICATION_TOPIC, 2, (short) 3);
    }

    @Bean
    NewTopic statistic() {
        // topic name, partition numbers, replication number
        return new NewTopic(TopicUtil.STATISTIC_TOPIC, 1, (short) 3);
    }
}
