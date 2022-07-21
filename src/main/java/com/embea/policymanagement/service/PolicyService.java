package com.embea.policymanagement.service;

import com.embea.policymanagement.dto.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

public interface PolicyService {

    PolicyCreationResponseDto createPolicy(PolicyCreationRequestDto policyCreationRequestDto);

    PolicyModificationResponseDto modifyPolicy(PolicyModificationRequestDto policyModificationRequestDto);

    PolicyInformationResponseDto getPolicy(String policyId, Date requestDate);

}
