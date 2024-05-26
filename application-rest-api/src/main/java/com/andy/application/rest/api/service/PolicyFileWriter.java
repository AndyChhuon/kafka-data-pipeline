package com.andy.application.rest.api.service;

import com.andy.entities.enums.PolicyDataStatus;
import com.andy.persistence.model.InsurancePolicyEntityMongoDb;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PolicyFileWriter {
    public UUID writeToFileAndUpdateStatus(List<InsurancePolicyEntityMongoDb> insurancePolicyEntityMongoDbList) {
        try {

            final UUID uuid = UUID.randomUUID();
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("policies-%s",uuid)));
            int count = 0;
            for (InsurancePolicyEntityMongoDb insurancePolicyEntityMongoDb : insurancePolicyEntityMongoDbList) {
                insurancePolicyEntityMongoDb.setRequestStatus(PolicyDataStatus.SENT);
                writer.append(String.format("Tx #%d: %s\n", count, insurancePolicyEntityMongoDb.getPolicyProcessedString()));
                count++;
            }

            writer.close();

            return uuid;

        }catch (IOException e){
            System.out.printf("failed to create file %s", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
