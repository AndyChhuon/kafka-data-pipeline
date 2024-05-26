package com.andy.processor.event;

import com.andy.entities.enums.PolicyDataStatus;
import com.andy.entities.event.InsurancePolicyEvent;
import com.andy.persistence.mapper.InsurancePolicyEntityMapper;
import com.andy.persistence.model.InsurancePolicyEntityMongoDb;
import com.andy.persistence.repository.InsurancePolicyEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@ComponentScan(basePackages = {"com.andy.persistence.repository"})
@EnableMongoRepositories(basePackageClasses = {InsurancePolicyEntityRepository.class})
public class KafkaEventProcessor {
    private InsurancePolicyEntityRepository insurancePolicyEntityRepository;


    public void processEvent(InsurancePolicyEvent event){
        System.out.println("Listener received "+ event);
        String processedString = convertToAl3(event);

        InsurancePolicyEntityMongoDb insurancePolicyEntityMongoDb = InsurancePolicyEntityMapper.INSTANCE.toInsurancePolicyEntity(event);
        insurancePolicyEntityMongoDb.setPolicyProcessedString(processedString);
        insurancePolicyEntityMongoDb.setRequestStatus(PolicyDataStatus.READY_TO_SEND);

        insurancePolicyEntityRepository.save(insurancePolicyEntityMongoDb);
        System.out.println("Processed Insurance Policy and saved "+ insurancePolicyEntityMongoDb);

    }

    private String convertToAl3(InsurancePolicyEvent event){
        return String.format("AL3 Processed String %s", event.toString());
    }
}
