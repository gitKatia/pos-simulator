package com.kat.possimulator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-pos-simulator";
    private String posTopic;
    private int posTopicReplicas;
    private int posTopicPartitions;
}
