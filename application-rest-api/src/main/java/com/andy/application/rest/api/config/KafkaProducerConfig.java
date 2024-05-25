package com.andy.application.rest.api.config;

import com.andy.application.rest.api.serializer.InsurancePolicyEventSerializer;
import com.andy.entities.event.InsurancePolicyEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> kafkaProducerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // because we are dealing with strings
        props.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, InsurancePolicyEventSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, InsurancePolicyEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfig()); // Allows us to create kafka producers, which send messages to a topic
    }
    @Bean
    public KafkaTemplate<String, InsurancePolicyEvent> kafkaTemplate(ProducerFactory<String, InsurancePolicyEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory); // Provides convenience methods for sending messages to kafka topics
    }

}
