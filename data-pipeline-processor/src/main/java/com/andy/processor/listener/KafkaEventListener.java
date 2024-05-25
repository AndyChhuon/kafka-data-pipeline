package com.andy.processor.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListener {
    @KafkaListener(
            topics = "#{'${spring.kafka.topic.name}'}",
            groupId = "listener-1"
    )
    void listen(String data){
        System.out.println("Listener received "+ data);
    }
}
