package com.embea.policymanagement.repository;

import com.embea.policymanagement.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    Optional<Insurance> findByPolicyId(String policyId);
}
