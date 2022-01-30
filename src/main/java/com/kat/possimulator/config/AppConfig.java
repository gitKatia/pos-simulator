package com.kat.possimulator.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final TopicsProperties topicsProperties;

    // not a good practice to create topics programmatically except for development purposes
    @Bean
    public NewTopic posTopic() {
        return TopicBuilder.name(topicsProperties.getPosTopic())
                .partitions(topicsProperties.getPosTopicPartitions())
                .replicas(topicsProperties.getPosTopicReplicas())
                .build();
    }
}
