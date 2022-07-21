package com.embea.policymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyModificationRequestDto {
    private String policyId;
    private Date effectiveDate;
    List<InsuredPersonDto> list;
}
