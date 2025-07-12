package com.project.hrm.recruitment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.department.entity.Role;
import com.project.hrm.recruitment.enums.RecruitmentRequirementsStatus;
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
public class RecruitmentRequirements {
    @Id
    private Integer id;
    private String description;
    private Integer quantity;
    private String expectedSalary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentRequirementsStatus status;

    private LocalDateTime dateRequired;

    @ManyToOne
    @JoinColumn
    private Role role;

    @ManyToOne
    @JoinColumn(name = "on_upload")
    @JsonBackReference
    private Employees requirements; // request

}
