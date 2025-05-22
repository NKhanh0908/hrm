package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.enums.RecruitmentRequirementsStatus;
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
public class RecruitmentRequirements {
    @Id
    private Integer id = IdGenerator.getGenerationId();
    private String description;
    private String positions;
    private Integer quantity;
    private String expectedSalary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentRequirementsStatus status;
    
    private LocalDateTime dateRequired;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Departments departments;

    @ManyToOne
    @JoinColumn(name = "on_upload")
    @JsonBackReference
    private Employees employees;

}
