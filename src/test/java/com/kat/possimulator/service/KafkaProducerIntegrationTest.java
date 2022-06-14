package com.kat.possimulator.service;

import com.kat.possimulator.config.TopicsProperties;
import com.kat.possimulator.model.PosInvoice;
import com.kat.possimulator.service.datagenerator.PosInvoiceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.concurrent.ListenableFuture;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import static com.kat.possimulator.TestConfig.POS_TEST_TOPIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@Slf4j
@DirtiesContext
public class KafkaProducerIntegrationTest {

   @Autowired
   private KafkaTemplate kafkaTemplate;

   @Autowired
   private TopicsProperties topicsProperties;

   @Autowired
   private PosInvoiceGenerator posInvoiceGenerator;

    @Container
    private static KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.kafka.properties.bootstrap.servers", () -> KAFKA_CONTAINER.getBootstrapServers());
        registry.add("kafka-pos-simulator.pos-topic", () -> POS_TEST_TOPIC);
        registry.add("spring.kafka.consumer.properties.auto.offset.reset", () -> "earliest");
    }

    @Test
    void testKafkaIsRunning() {
        assertTrue(KAFKA_CONTAINER.isRunning());
    }

    @DisplayName("producer sends record to the topic")
    @Test
    void testProducerSendsRecordToTheTopic() throws InterruptedException {

        // Given
        PosInvoice posInvoice = posInvoiceGenerator.generatePosInvoice();
        KafkaConsumer<String, PosInvoice> consumer = kafkaConsumer();
        consumer.subscribe(Collections.singleton(topicsProperties.getPosTopic()));

        // When
        ListenableFuture<SendResult<String,String>> result = kafkaTemplate.send(topicsProperties.getPosTopic(), posInvoice.getStoreId(), posInvoice);
        while(!result.isDone()) {
            Thread.sleep(1000);
        }

        // Then
        ConsumerRecords<String, PosInvoice> records = KafkaTestUtils.getRecords(consumer);
        List<PosInvoice> invoices = new ArrayList<>();
        records.forEach(r -> invoices.add(r.value()));
        assertThat(invoices).contains(posInvoice);
    }

    private KafkaConsumer<String, PosInvoice> kafkaConsumer() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new KafkaConsumer<>(properties);
    }
}
