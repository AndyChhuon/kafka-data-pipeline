package com.andy.entities.event;

import com.andy.entities.datapipeline.PolicyDataStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsurancePolicyEvent {
    private UUID eventUuid;
    private UUID policyUuid;
    private String underwritingCompany;
    private List<String> contractTypes;
    private String requestor;
    private LocalDateTime creationDate;
    private PolicyDataStatus requestStatus;
}
