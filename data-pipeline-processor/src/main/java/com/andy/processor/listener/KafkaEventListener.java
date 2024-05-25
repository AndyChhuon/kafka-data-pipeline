package com.andy.processor.listener;

import com.andy.entities.event.InsurancePolicyEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListener {
    @KafkaListener(
            topics = "#{'${spring.kafka.topic.name}'}",
            groupId = "listener-1")
    void listen(InsurancePolicyEvent data){
        System.out.println("Listener received "+ data);
    }
}
