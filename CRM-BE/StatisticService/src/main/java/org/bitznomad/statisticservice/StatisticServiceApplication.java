package org.bitznomad.statisticservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@SpringBootApplication
public class StatisticServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticServiceApplication.class, args);
    }
    @Bean
    JsonMessageConverter converter() {
        return new JsonMessageConverter();
    }

//    @Bean
//    DefaultErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
//                new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2)
//        );
//        errorHandler.setRetryListeners((record, exception, deliveryAttempt) -> {
//            System.out.printf("Failed to process record: %s, Exception: %s, Attempt: %d%n",
//                    record, exception.getMessage(), deliveryAttempt);
//        });
//        return errorHandler;
//    }

}
