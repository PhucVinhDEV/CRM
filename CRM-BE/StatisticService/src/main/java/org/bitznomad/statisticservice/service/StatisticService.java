package org.bitznomad.statisticservice.service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bitznomad.statisticservice.model.Statistic;
import org.bitznomad.statisticservice.repository.StatisticRepo;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class StatisticService {
    @PersistenceContext
    private EntityManager entityManager;
    private final StatisticRepo statisticsRepository;
//
//    @KafkaListener(id = "statisticGroup", topics = "statistic")
//    public void listen(Statistic statistic) {
//            log.info("Received: " + statistic);
//            throw new RuntimeException("Exception ne");
//    }
//
//    @KafkaListener(id = "dltGroup3", topics = "statistic-dlt")
//    public void dltListen(Statistic statistic) {
//        try {
//            log.error("Received from DLT: " + statistic);
//            System.out.println(statistic);
//            // Xử lý logic với message từ DLT ở đây, nhưng không throw exception
//        } catch (Exception e) {
//            // Chỉ log lỗi, không throw exception
//            log.error("Error processing DLT message: {}", e.getMessage(), e);
//        }
//    }

    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listenStatistic(Statistic statistic) {
        log.info("Received statistic: {}", statistic);
        // Xử lý logic hoặc throw exception để kích hoạt retry
        if(statisticsRepository.existsById(statistic.getId())){
            entityManager.merge(statistic);
        }
        statisticsRepository.saveAndFlush(statistic);
    }

    @DltHandler
    public void dltListen(Object message, @Header(KafkaHeaders.ORIGINAL_TOPIC) String topic,
                          @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
        try {
            log.info("Received from DLT for topic {}: {}", topic, message);
            log.info("Exception was: {}", exceptionMessage);

            // Xử lý khác nhau dựa vào topic gốc
            if (topic.equals("statistic")) {
                // Xử lý riêng cho statistic
                log.error(exceptionMessage);
            } else if (topic.equals("notification")) {
                // Xử lý riêng cho notification
            }
        } catch (Exception e) {
            log.error("Error processing DLT message: {}", e.getMessage(), e);
        }
    }
}
