package com.andy.application.rest.api.service;

import com.andy.application.rest.api.config.KafkaTopicConfig;
import com.andy.application.rest.api.mapper.InsurancePolicyEventMapper;
import com.andy.application.rest.api.request.PolicyDataInputDTO;
import com.andy.entities.event.InsurancePolicyEvent;
import com.andy.persistence.mapper.InsurancePolicyEntityMapper;
import com.andy.persistence.repository.InsurancePolicyEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InsurancePolicyService {
    private KafkaTemplate<String, InsurancePolicyEvent> kafkaTemplate;
    private KafkaTopicConfig kafkaTopicConfig;
    private InsurancePolicyEntityRepository insurancePolicyEntityRepository;

    public InsurancePolicyEvent publish(PolicyDataInputDTO policyDataInputDTO){
        System.out.println("Received " + policyDataInputDTO);
        InsurancePolicyEvent event = InsurancePolicyEventMapper.INSTANCE.toInsurancePolicyEvent(policyDataInputDTO);
        kafkaTemplate.send(kafkaTopicConfig.getKafkaTopicName(), event);
        insurancePolicyEntityRepository.save(InsurancePolicyEntityMapper.INSTANCE.toInsurancePolicyEntity(event));
        System.out.println("Sent " + event);

        return event;
    }
}
