package com.andy.entities.event;

import com.andy.entities.enums.PolicyDataStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsurancePolicyEvent {
    private String eventUuid;
    private String policyUuid;
    private String underwritingCompany;
    private List<String> contractTypes;
    private String requestor;
    private LocalDateTime creationDate;
    private PolicyDataStatus requestStatus;
}
