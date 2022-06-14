package com.kat.possimulator;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TestConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    public static final String POS_TEST_TOPIC = "pos_test_x_topic";

    @Bean
    public NewTopic posTestTopic() {
        return new NewTopic(POS_TEST_TOPIC, 1, (short)1);
    }
}
