package com.andy.application.rest.api.mapper;

import com.andy.application.rest.api.request.PolicyDataInputDTO;
import com.andy.entities.event.InsurancePolicyEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface InsurancePolicyEventMapper {
    InsurancePolicyEventMapper INSTANCE = Mappers.getMapper(InsurancePolicyEventMapper.class);

    @Mapping(target = "eventUuid", expression = "java(generateUUID())")
    InsurancePolicyEvent toInsurancePolicyEvent(
            PolicyDataInputDTO policyDataInputDTO
    );

    default UUID generateUUID() {
        return UUID.randomUUID();
    }
}
