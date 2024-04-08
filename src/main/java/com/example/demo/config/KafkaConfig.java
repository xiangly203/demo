package com.example.demo.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaConfig {
    String myTopic = "MyTopic";

    @Bean
    public NewTopic myTopic() {
        return TopicBuilder.name(myTopic)
                .partitions(10)
                .replicas(3)
                .compact()
                .build();
    }

}
