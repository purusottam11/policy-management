package com.embea.policymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyCreationRequestDto {
    private Date startDate;
    List<InsuredPersonDto> list;
}
