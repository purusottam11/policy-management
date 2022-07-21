package com.embea.policymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyInformationResponseDto {
    private String policyId;
    private Date requestDate;
    List<InsuredPersonDto> list;
    private Double totalPremium;
}
