package com.andy.application.rest.api.serializer;

import com.andy.entities.event.InsurancePolicyEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class InsurancePolicyEventSerializer implements Serializer<InsurancePolicyEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, InsurancePolicyEvent insurancePolicyEvent) {
        try {
            if (insurancePolicyEvent == null){
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing...");

            objectMapper.registerModule(new JavaTimeModule()); // needed for LocalDateTime
            return objectMapper.writeValueAsBytes(insurancePolicyEvent);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing InsurancePolicyEvent to byte[]");
        }
    }

    @Override
    public void close() {
    }
}