package com.embea.policymanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "insurance")
public class Insurance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long insuranceId;
    @Column(name = "policy_id", unique = true, nullable = false)
    private String policyId;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "effective_date")
    private Date effectiveDate;
    @Column(name = "total_premium")
    private Double totalPremium;
    @OneToMany(mappedBy = "insuredPerson", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<InsuredPerson> insuredPersonList;
}
