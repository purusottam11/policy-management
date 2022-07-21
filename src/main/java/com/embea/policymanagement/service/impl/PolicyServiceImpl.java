package com.embea.policymanagement.service.impl;

import com.embea.policymanagement.dto.*;
import com.embea.policymanagement.model.Insurance;
import com.embea.policymanagement.model.InsuredPerson;
import com.embea.policymanagement.repository.InsuranceRepository;
import com.embea.policymanagement.repository.InsuredPersonRepository;
import com.embea.policymanagement.service.PolicyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private InsuredPersonRepository insuredPersonRepository;

    @Override
    @Transactional
    public PolicyCreationResponseDto createPolicy(PolicyCreationRequestDto policyCreationRequestDto) {
        Insurance insurance = mapToInsurance(policyCreationRequestDto);
        insurance = insuranceRepository.save(insurance);
        List<InsuredPerson> insuredPersonList = policyCreationRequestDto.getList().stream()
                .map(i -> mapToInsuredPerson(i)).collect(Collectors.toList());
        for (InsuredPerson insuredPerson : insuredPersonList) {
            insuredPerson.setInsurance(insurance);
        }
        insuredPersonList = insuredPersonRepository.saveAll(insuredPersonList);
        PolicyCreationResponseDto policyCreationResponseDto = new PolicyCreationResponseDto();
        BeanUtils.copyProperties(policyCreationRequestDto, policyCreationResponseDto);
        policyCreationResponseDto.setPolicyId(insurance.getPolicyId());
        policyCreationResponseDto.setTotalPremium(insurance.getTotalPremium());
        return policyCreationResponseDto;
    }

    @Override
    @Transactional
    public PolicyModificationResponseDto modifyPolicy(PolicyModificationRequestDto policyModificationRequestDto) {
        Insurance insurance = mapToInsurance(policyModificationRequestDto);
        Insurance savedInsurance = insuranceRepository.findByPolicyId(insurance.getPolicyId()).orElseThrow(
                () -> new RuntimeException("Insurance does not exist with policyId !!"));
        insurance.setInsuranceId(savedInsurance.getInsuranceId());
        insurance = insuranceRepository.save(insurance);
        List<InsuredPerson> insuredPersonList = policyModificationRequestDto.getList().stream()
                .map(i -> mapToInsuredPerson(i)).collect(Collectors.toList());
        List<InsuredPerson> modifiedInsuredPersonList = new ArrayList<>();
        for (InsuredPerson insuredPerson : insuredPersonList) {
            InsuredPerson existInsuredPerson = insuredPersonRepository.
                    findByInsurancePolicyIdAndFirstNameAndSecondName(insurance.getPolicyId(), insuredPerson.getFirstName(), insuredPerson.getSecondName()).get();
            if (existInsuredPerson != null) {
                insuredPerson.setInsuredPersonId(existInsuredPerson.getInsuredPersonId());
            }
            insuredPerson.setInsurance(insurance);
            modifiedInsuredPersonList.add(insuredPerson);
        }
        insuredPersonRepository.saveAll(modifiedInsuredPersonList);
        PolicyModificationResponseDto policyModificationResponseDto = new PolicyModificationResponseDto();
        BeanUtils.copyProperties(policyModificationRequestDto, policyModificationResponseDto);
        policyModificationResponseDto.setTotalPremium(insurance.getTotalPremium());
        return policyModificationResponseDto;
    }

    @Override
    public PolicyInformationResponseDto getPolicy(String policyId, Date requestDate) {
        Insurance insurance = insuranceRepository.findByPolicyId(policyId).orElseThrow(
                () -> new RuntimeException("Insurance does not exist with policyId !!"));
        PolicyInformationResponseDto policyInformationResponseDto = new PolicyInformationResponseDto();
        policyInformationResponseDto.setRequestDate(requestDate);
        policyInformationResponseDto.setPolicyId(policyId);
        policyInformationResponseDto.setTotalPremium(insurance.getTotalPremium());
        List<InsuredPersonDto> insuredPersonDtoList = new ArrayList<>();
        for (InsuredPerson insuredPerson : insurance.getInsuredPersonList()) {
            InsuredPersonDto insuredPersonDto = new InsuredPersonDto();
            BeanUtils.copyProperties(insurance, insuredPersonDto);
            insuredPersonDtoList.add(insuredPersonDto);
        }
        return policyInformationResponseDto;
    }

    private InsuredPerson mapToInsuredPerson(InsuredPersonDto insuredPersonDto) {
        InsuredPerson insuredPerson = new InsuredPerson();
        insuredPerson.setFirstName(insuredPersonDto.getFirstName());
        insuredPerson.setSecondName(insuredPersonDto.getSecondName());
        insuredPerson.setPremium(insuredPersonDto.getPremium());
        return insuredPerson;
    }

    private Insurance mapToInsurance(PolicyCreationRequestDto policyCreationRequestDto) {
        Insurance insurance = new Insurance();
        insurance.setPolicyId(UUID.randomUUID().toString());
        insurance.setStartDate(policyCreationRequestDto.getStartDate());
        Double totalPremium = policyCreationRequestDto.getList().stream().mapToDouble(i -> i.getPremium()).sum();
        insurance.setTotalPremium(totalPremium);
        return insurance;
    }

    private Insurance mapToInsurance(PolicyModificationRequestDto policyModificationRequestDto) {
        Insurance insurance = new Insurance();
        insurance.setPolicyId(policyModificationRequestDto.getPolicyId());
        insurance.setEffectiveDate(policyModificationRequestDto.getEffectiveDate());
        Double totalPremium = policyModificationRequestDto.getList().stream().mapToDouble(i -> i.getPremium()).sum();
        insurance.setTotalPremium(totalPremium);
        return insurance;
    }


}
