package com.andy.application.rest.api;


import com.andy.application.rest.api.request.PolicyDataInputDTO;

import com.andy.application.rest.api.service.InsurancePolicyService;
import com.andy.entities.event.InsurancePolicyEvent;
import com.andy.persistence.repository.InsurancePolicyEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/messages")
@AllArgsConstructor
@ComponentScan(basePackages = {"com.andy.persistence.repository"})
@EnableMongoRepositories(basePackageClasses = {InsurancePolicyEntityRepository.class})
public class MessageController {

    private InsurancePolicyService insurancePolicyService;

    @PostMapping
    public ResponseEntity<InsurancePolicyEvent> publish(@RequestBody PolicyDataInputDTO policyDataInputDTO){
        InsurancePolicyEvent insurancePolicyEvent = insurancePolicyService.publish(policyDataInputDTO);
        return ResponseEntity.ok().body(insurancePolicyEvent);
    }

    @GetMapping(path = "send")
    public ResponseEntity<String> send(){
        insurancePolicyService.sendPolicies();
        return ResponseEntity.ok().body("Email Successfully sent");

    }

}
