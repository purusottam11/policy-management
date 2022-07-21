package com.embea.policymanagement.controller;

import com.embea.policymanagement.dto.*;
import com.embea.policymanagement.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/policy")
@RestController
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping("/create")
    public ResponseEntity<PolicyCreationResponseDto> createPolicy(@RequestBody PolicyCreationRequestDto policyCreationRequestDto) {
        return new ResponseEntity<>(policyService.createPolicy(policyCreationRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/modify")
    public ResponseEntity<PolicyModificationResponseDto> modifyPolicy(@RequestBody PolicyModificationRequestDto policyModificationRequestDto) {
        return new ResponseEntity<>(policyService.modifyPolicy(policyModificationRequestDto), HttpStatus.OK);
    }

    @GetMapping("/get/{policyId}/{requestDate}")
    public ResponseEntity<PolicyInformationResponseDto> getPolicy(@PathVariable(name = "policyId") String policyId, @PathVariable(name = "requestDate") Date requestDate) {
        return new ResponseEntity<>(policyService.getPolicy(policyId, requestDate), HttpStatus.OK);
    }


}
