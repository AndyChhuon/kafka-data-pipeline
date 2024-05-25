package com.andy.application.rest.api;

import com.andy.application.rest.api.config.KafkaTopicConfig;
import com.andy.application.rest.api.mapper.InsurancePolicyEventMapper;
import com.andy.application.rest.api.request.PolicyDataInputDTO;
import com.andy.entities.event.InsurancePolicyEvent;
import com.andy.persistence.mapper.InsurancePolicyEntityMapper;
import com.andy.persistence.repository.InsurancePolicyEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.ComponentScan;

@RestController
@RequestMapping("api/v1/messages")
@AllArgsConstructor
@ComponentScan(basePackages = {"com.andy.persistence.repository"})
@EnableMongoRepositories(basePackageClasses = {InsurancePolicyEntityRepository.class})
public class MessageController {

    private KafkaTemplate<String, InsurancePolicyEvent> kafkaTemplate;
    private KafkaTopicConfig kafkaTopicConfig;
    private InsurancePolicyEntityRepository insurancePolicyEntityRepository;

    @PostMapping
    public void publish(@RequestBody PolicyDataInputDTO policyDataInputDTO){
        System.out.println("Received " + policyDataInputDTO);
        InsurancePolicyEvent event = InsurancePolicyEventMapper.INSTANCE.toInsurancePolicyEvent(policyDataInputDTO);
        kafkaTemplate.send(kafkaTopicConfig.getKafkaTopicName(), event);
        insurancePolicyEntityRepository.save(InsurancePolicyEntityMapper.INSTANCE.toInsurancePolicyEntity(event));
        System.out.println("Sent " + event);
    }
}
