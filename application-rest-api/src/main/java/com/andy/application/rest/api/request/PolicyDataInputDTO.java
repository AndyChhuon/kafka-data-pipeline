package com.andy.application.rest.api.request;

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
public class PolicyDataInputDTO {
    private UUID policyUuid;
    private String underwritingCompany;
    private List<String> contractTypes;
    private String requestor;
    private LocalDateTime creationDate = LocalDateTime.now();
    private PolicyDataStatus requestStatus = PolicyDataStatus.READY_TO_PROCESS;
}
