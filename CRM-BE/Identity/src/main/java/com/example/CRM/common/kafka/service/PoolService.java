package com.example.CRM.common.kafka.service;

import com.example.CRM.common.kafka.util.TopicUtil;
import com.example.CRM.mail.MailAuthen.model.MessageMail;
import com.example.CRM.mail.MailAuthen.repository.MessageMailRepository;
import com.example.CRM.statistic.model.StatisticDTO;
import com.example.CRM.statistic.repository.StatisticRepository;
import io.micrometer.core.instrument.Statistic;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@AllArgsConstructor
@Slf4j
public class PoolService {
    private final MessageMailRepository messageMailRepository;
    private final StatisticRepository statisticRepository;
    private final KafkaTemplate kafkaTemplate;

    @Scheduled(fixedDelay = 1000)
    public void producer(){
        List<MessageMail> messageMails = messageMailRepository.findByStatus(false);
        for (MessageMail messageMail : messageMails) {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TopicUtil.NOTIFICATION_TOPIC, messageMail);
            // Add a callback to handle success and failure scenarios using CompletableFuture API
            future.whenComplete((result, exception) -> {
                if (exception == null) {
                    log.info("Sent message=[" + messageMail.getId() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                    messageMail.setStatus(true);// success
                    messageMailRepository.save(messageMail);
                } else {
                log.error("Unable to send message=[" +
                        messageMail.getId() + "] due to : " + exception.getMessage());
                }
            });
            List<StatisticDTO> statistics = statisticRepository.findByStatus(false);
            for (StatisticDTO statisticDTO : statistics) {
                CompletableFuture<SendResult<String, Object>> future1 = kafkaTemplate.send(TopicUtil.STATISTIC_TOPIC, statisticDTO);
                future1.whenComplete((result, exception) -> {
                    if (exception == null) {
                        log.info("Sent message=[" + statisticDTO.getId() +
                                "] with offset=[" + result.getRecordMetadata().offset() + "]");
                        statisticDTO.setStatus(true);// success
                        statisticRepository.save(statisticDTO);
                    }else {
                        log.error("Unable to send message=[" +
                                statisticDTO.getId() + "] due to : " + exception.getMessage());
                    }
                });
            }
        }

    }

    @Scheduled(fixedDelay = 60000)
    public void delete() {
        List<MessageMail> messageDTOs = messageMailRepository.findByStatus(true);
        messageMailRepository.deleteAllInBatch(messageDTOs);

        List<StatisticDTO> statisticDTOs = statisticRepository.findByStatus(true);
        statisticRepository.deleteAllInBatch(statisticDTOs);
    }
}
