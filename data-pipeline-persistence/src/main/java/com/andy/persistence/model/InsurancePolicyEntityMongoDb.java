package com.andy.persistence.model;

import com.andy.entities.enums.PolicyDataStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "insurance-policy-entities")
public class InsurancePolicyEntityMongoDb {
    @Id
    @NotNull
    private String eventUuid;
    @NotNull
    private String policyUuid;
    private String underwritingCompany;
    private List<String> contractTypes;
    private String requestor;
    private LocalDateTime creationDate;
    private String policyProcessedString;
    @NotNull
    private PolicyDataStatus requestStatus;
}
