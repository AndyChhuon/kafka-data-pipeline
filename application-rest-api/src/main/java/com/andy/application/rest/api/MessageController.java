package com.andy.application.rest.api;

import com.andy.application.rest.api.config.KafkaTopicConfig;
import com.andy.application.rest.api.mapper.InsurancePolicyEventMapper;
import com.andy.application.rest.api.request.PolicyDataInputDTO;
import com.andy.entities.event.InsurancePolicyEvent;
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

    private KafkaTemplate<String, InsurancePolicyEvent> kafkaTemplate;
    private KafkaTopicConfig kafkaTopicConfig;

    @PostMapping
    public void publish(@RequestBody PolicyDataInputDTO policyDataInputDTO){
        kafkaTemplate.send(kafkaTopicConfig.getKafkaTopicName(), InsurancePolicyEventMapper.INSTANCE.toInsurancePolicyEvent(policyDataInputDTO));
    }
}
