package org.bitznomad.statisticservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;

@Configuration
public class KafkaRetryConfig {
    @Bean
    public RetryTopicConfiguration retryTopicConfiguration(
            KafkaTemplate<Object, Object> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .maxAttempts(3)
                .fixedBackOff(1000)
                .includeTopic("statistic")
                .includeTopic("notification")
                .create(template);
    }
}
