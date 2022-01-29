package com.kat.possimulator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final TopicsProperties topicsProperties;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper =  new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    // not a good practice to create topics programmatically except for development purposes
    @Bean
    public NewTopic posTopic() {
        return TopicBuilder.name(topicsProperties.getPosTopic())
                .partitions(topicsProperties.getPosTopicPartitions())
                .replicas(topicsProperties.getPosTopicReplicas())
                .build();
    }
}
