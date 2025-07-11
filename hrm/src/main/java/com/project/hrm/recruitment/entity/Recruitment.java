package com.project.hrm.recruitment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.recruitment.enums.RecruitmentStatus;
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
public class Recruitment {
    @Id
    private Integer id;
    private String contactPhone;
    private String email;
    private LocalDateTime deadline;
    private String jobDescription;
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentStatus status;

    @OneToOne
    @JoinColumn
    private RecruitmentRequirements recruitmentRequirements;

    @ManyToOne
    @JoinColumn(name = "on_approve")
    @JsonBackReference
    private Employees approveBy;

}
