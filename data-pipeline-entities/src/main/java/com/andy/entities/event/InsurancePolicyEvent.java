package com.andy.entities.event;

import com.andy.entities.enums.PolicyDataStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;
    private PolicyDataStatus requestStatus;
}
