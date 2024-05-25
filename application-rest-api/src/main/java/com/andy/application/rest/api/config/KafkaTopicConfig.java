package com.andy.application.rest.api.config;

import lombok.Getter;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Getter
public class KafkaTopicConfig {
    @Value("${spring.kafka.topic.name}")
    private String kafkaTopicName;
    @Bean
    public NewTopic insurancePolicyTopic(){

        return TopicBuilder.name(kafkaTopicName).build();
    }
}
