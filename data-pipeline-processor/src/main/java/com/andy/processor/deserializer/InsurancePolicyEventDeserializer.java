package com.andy.processor.deserializer;

import com.andy.entities.event.InsurancePolicyEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class InsurancePolicyEventDeserializer implements Deserializer<InsurancePolicyEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public InsurancePolicyEvent deserialize(String topic, byte[] data) {
        System.out.println("Insurance Policy Event Deserialized");
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");

            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(data, InsurancePolicyEvent.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to InsurancePolicyEvent");
        }
    }

    @Override
    public void close() {
    }
}