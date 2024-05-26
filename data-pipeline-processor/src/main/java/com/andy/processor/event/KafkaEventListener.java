package com.andy.processor.event;

import com.andy.entities.event.InsurancePolicyEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaEventListener {
    private KafkaEventProcessor eventProcessor;

    @KafkaListener(
            topics = "#{'${spring.kafka.topic.name}'}",
            groupId = "listener-1")
    void listen(InsurancePolicyEvent event){
        eventProcessor.processEvent(event);
    }
}
