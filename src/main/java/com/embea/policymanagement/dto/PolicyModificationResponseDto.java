package com.embea.policymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyModificationResponseDto {
    private String policyId;
    private Date effectiveDate;
    List<InsuredPersonDto> list;
    private Double totalPremium;
}
