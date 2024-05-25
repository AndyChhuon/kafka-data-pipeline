package com.andy.persistence.mapper;

import com.andy.entities.event.InsurancePolicyEvent;
import com.andy.persistence.model.InsurancePolicyEntityMongoDb;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface InsurancePolicyEntityMapper {
    InsurancePolicyEntityMapper INSTANCE = Mappers.getMapper(InsurancePolicyEntityMapper.class);

    InsurancePolicyEntityMongoDb toInsurancePolicyEntity(
            InsurancePolicyEvent insurancePolicyEvent
    );
}
