package com.example.CRM.common.kafka.service;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaService {

    private final KafkaAdmin kafkaAdmin;

    public void createTopic(String topicName, int partitions, int replicas) {
        NewTopic newTopic = new NewTopic(topicName, partitions, (short) replicas);
        kafkaAdmin.createOrModifyTopics(newTopic);
    }
}
