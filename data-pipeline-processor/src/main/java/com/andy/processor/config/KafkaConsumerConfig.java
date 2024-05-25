package com.andy.processor.config;

import com.andy.entities.event.InsurancePolicyEvent;
import com.andy.processor.deserializer.InsurancePolicyEventDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> kafkaConsumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // because we are dealing with strings
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, InsurancePolicyEventDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, InsurancePolicyEvent> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaConsumerConfig()); // Allows us to create kafka consumers, which subscribes and listens to kafka
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, InsurancePolicyEvent>> kafkaListenerContainerFactory(ConsumerFactory<String, InsurancePolicyEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, InsurancePolicyEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory); // Pass in kafka consumer to create a kafka listener factory
        return factory;
    }
}
