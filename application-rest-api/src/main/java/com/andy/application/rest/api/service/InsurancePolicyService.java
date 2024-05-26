package com.andy.application.rest.api.service;

import com.andy.application.rest.api.config.KafkaTopicConfig;
import com.andy.application.rest.api.mapper.InsurancePolicyEventMapper;
import com.andy.application.rest.api.request.PolicyDataInputDTO;
import com.andy.entities.enums.PolicyDataStatus;
import com.andy.entities.event.InsurancePolicyEvent;
import com.andy.persistence.mapper.InsurancePolicyEntityMapper;
import com.andy.persistence.model.InsurancePolicyEntityMongoDb;
import com.andy.persistence.repository.InsurancePolicyEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InsurancePolicyService {
    private KafkaTemplate<String, InsurancePolicyEvent> kafkaTemplate;
    private KafkaTopicConfig kafkaTopicConfig;
    private InsurancePolicyEntityRepository insurancePolicyEntityRepository;
    private PolicyFileWriter policyFileWriter;
    private EmailService emailService;

    public InsurancePolicyEvent publish(PolicyDataInputDTO policyDataInputDTO){
        System.out.println("Received " + policyDataInputDTO);
        InsurancePolicyEvent event = InsurancePolicyEventMapper.INSTANCE.toInsurancePolicyEvent(policyDataInputDTO);
        kafkaTemplate.send(kafkaTopicConfig.getKafkaTopicName(), event);
        insurancePolicyEntityRepository.save(InsurancePolicyEntityMapper.INSTANCE.toInsurancePolicyEntity(event));
        System.out.println("Sent " + event);

        return event;
    }

    public void sendPolicies(){
            List<InsurancePolicyEntityMongoDb> readyToSendTransactions = insurancePolicyEntityRepository.findByRequestStatus(PolicyDataStatus.READY_TO_SEND);
            UUID fileUuid = policyFileWriter.writeToFileAndUpdateStatus(readyToSendTransactions);

            emailService.send("broker123@gmail.com", fileUuid);
            insurancePolicyEntityRepository.saveAll(readyToSendTransactions);

            System.out.println("Sent files" + fileUuid);
    }
}
