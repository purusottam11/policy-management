package com.embea.policymanagement.repository;

import com.embea.policymanagement.model.InsuredPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsuredPersonRepository extends JpaRepository<InsuredPerson, Long> {
    Optional<InsuredPerson> findByInsurancePolicyIdAndFirstNameAndSecondName(String policyId, String firstName, String secondName);
}
