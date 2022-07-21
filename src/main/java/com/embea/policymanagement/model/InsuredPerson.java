package com.embea.policymanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "insured_person")
public class InsuredPerson implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long insuredPersonId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "second_name", nullable = false)
    private String secondName;
    @Column(name = "premium", nullable = false)
    private Double premium;
    @JoinColumn(
            name = "insurance_id",
            referencedColumnName = "insuranceId"
    )
    @ManyToOne(cascade = CascadeType.DETACH)
    private Insurance insurance;
}
