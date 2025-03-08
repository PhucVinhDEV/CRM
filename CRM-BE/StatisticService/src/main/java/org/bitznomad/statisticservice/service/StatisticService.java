package org.bitznomad.statisticservice.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.bitznomad.statisticservice.model.Statistic;
import org.bitznomad.statisticservice.repository.StatisticRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class StatisticService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StatisticRepo statisticRepo;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listen(Statistic statistic) {
        try {
            log.info("Received: " + statistic);
            entityManager.merge(statistic);
//            throw new RuntimeException("failed");

        } catch (Exception e) {
            log.error("Error with details: " + e.getMessage(), e);
        }

    }


    @KafkaListener(id = "dltGroup3", topics = "statistic.DLT")
    public void dltListen(String in) {
        log.info("Received from DLT: ");
        System.out.println(in);
    }
}
