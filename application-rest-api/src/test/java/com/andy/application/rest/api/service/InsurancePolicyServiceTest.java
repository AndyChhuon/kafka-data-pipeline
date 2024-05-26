package com.andy.application.rest.api.service;

import com.andy.application.rest.api.config.KafkaTopicConfig;
import com.andy.application.rest.api.request.PolicyDataInputDTO;
import com.andy.entities.enums.PolicyDataStatus;
import com.andy.entities.event.InsurancePolicyEvent;
import com.andy.persistence.model.InsurancePolicyEntityMongoDb;
import com.andy.persistence.repository.InsurancePolicyEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.jeasy.random.EasyRandom;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InsurancePolicyServiceTest {
    @Mock
    private KafkaTemplate<String, InsurancePolicyEvent> kafkaTemplate;
    @Mock
    private KafkaTopicConfig kafkaTopicConfig;
    @Mock
    private InsurancePolicyEntityRepository insurancePolicyEntityRepository;
    @Mock
    private PolicyFileWriter policyFileWriter;
    @Mock
    private EmailService emailService;

    private InsurancePolicyService insurancePolicyService;
    private final EasyRandom easyRandom = new EasyRandom();

    @Captor
    private ArgumentCaptor<InsurancePolicyEvent> insurancePolicyEventArgumentCaptor = ArgumentCaptor.forClass(InsurancePolicyEvent.class);
    @Captor
    private ArgumentCaptor<InsurancePolicyEntityMongoDb> insurancePolicyEntityMongoDbArgumentCaptor = ArgumentCaptor.forClass(InsurancePolicyEntityMongoDb.class);
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

    @BeforeEach
    void setUp() {
        insurancePolicyService = new InsurancePolicyService(kafkaTemplate, kafkaTopicConfig, insurancePolicyEntityRepository, policyFileWriter, emailService);
    }

    @Test
    @DisplayName("When I receive a policyDataInputDTO, then a kafka event is published and saved into the repository")
    void policyDataInputDTO_publish() {
        //given
        when(kafkaTopicConfig.getKafkaTopicName()).thenReturn("kafka-topic-name");
        PolicyDataInputDTO policyDataInputDTO = easyRandom.nextObject(PolicyDataInputDTO.class);

        //when
        InsurancePolicyEvent returnedInsurancePolicyEvent = insurancePolicyService.publish(policyDataInputDTO);

        //then
        verify(kafkaTemplate).send(stringArgumentCaptor.capture(), insurancePolicyEventArgumentCaptor.capture());
        assertEquals("kafka-topic-name", stringArgumentCaptor.getValue());
        assertThat(policyDataInputDTO).usingRecursiveComparison().ignoringFields("eventUuid").isEqualTo(insurancePolicyEventArgumentCaptor.getValue());

        verify(insurancePolicyEntityRepository).save(insurancePolicyEntityMongoDbArgumentCaptor.capture());
        assertThat(insurancePolicyEventArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(insurancePolicyEntityMongoDbArgumentCaptor.getValue());

        assertThat(insurancePolicyEventArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(returnedInsurancePolicyEvent);
    }

    @Test
    void sendPolicies() {
        //given
        UUID fileUuid = UUID.randomUUID();
        List<InsurancePolicyEntityMongoDb> insurancePolicyEntityMongoDbList = easyRandom.objects(InsurancePolicyEntityMongoDb.class, 5).toList();

        when(insurancePolicyEntityRepository.findByRequestStatus(any())).thenReturn(insurancePolicyEntityMongoDbList);
        when(policyFileWriter.writeToFileAndUpdateStatus(any())).thenReturn(fileUuid);

        //when
        insurancePolicyService.sendPolicies();

        //then
        verify(insurancePolicyEntityRepository).findByRequestStatus(PolicyDataStatus.READY_TO_SEND);
        verify(policyFileWriter).writeToFileAndUpdateStatus(insurancePolicyEntityMongoDbList);
        verify(emailService).send("broker123@gmail.com", fileUuid);
        verify(insurancePolicyEntityRepository).saveAll(insurancePolicyEntityMongoDbList);


    }
}