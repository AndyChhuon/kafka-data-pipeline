package com.andy.application.rest.api;

import com.andy.application.rest.api.config.KafkaTopicConfig;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/messages")
@AllArgsConstructor
public class MessageController {

    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTopicConfig kafkaTopicConfig;

    @PostMapping
    public void publish(@RequestBody MessageRequest request){
        kafkaTemplate.send(kafkaTopicConfig.getKafkaTopicName(), request.message());
    }
}
