package com.example.demo.service;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class BookProducerService {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Object o) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, o);
        future.thenAccept(result -> log.info("Sent message=[{}] with offset=[{}]", result.getRecordMetadata().topic(), result.getRecordMetadata().offset()))
                .exceptionally(ex -> {
                    log.error("Unable to send message=[{}]",  ex.getMessage());
                    return null;
                });
    }
}
