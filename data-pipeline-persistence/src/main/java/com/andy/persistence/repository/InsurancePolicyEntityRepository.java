package com.andy.persistence.repository;

import com.andy.entities.enums.PolicyDataStatus;
import com.andy.persistence.model.InsurancePolicyEntityMongoDb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsurancePolicyEntityRepository extends MongoRepository<InsurancePolicyEntityMongoDb, String>  {
    List<InsurancePolicyEntityMongoDb> findByRequestStatus(PolicyDataStatus policyDataStatus);

}
