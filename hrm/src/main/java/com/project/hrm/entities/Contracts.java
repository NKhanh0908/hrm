package com.project.hrm.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.enums.ContractStatus;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contracts {
    @Id
    private Integer id = IdGenerator.getGenerationId();
    private String title;
    private LocalDateTime contractSigningDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double baseSalary;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus contractStatus;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Employees employee;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Departments departments;

    @ManyToOne
    @JoinColumn
    private Role role;

    public Contracts(Contracts contracts) {
        this.id = IdGenerator.getGenerationId();
        this.role = contracts.getRole();
        this.departments = contracts.getDepartments();
        this.employee = contracts.getEmployee();
        this.description = contracts.getDescription();
        this.baseSalary = contracts.getBaseSalary();
        this.endDate = contracts.getEndDate();
        this.startDate = contracts.getStartDate();
        this.contractStatus = contracts.getContractStatus();
        this.contractSigningDate = contracts.getContractSigningDate();
        this.title = contracts.getTitle();
    }
}
