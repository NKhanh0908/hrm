package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.enums.RecruitmentStatus;
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
public class Recruitment {
    @Id
    private Integer id = IdGenerator.getGenerationId();
    private String position;
    private String contactPhone;
    private String email;
    private LocalDateTime deadline;
    private String jobDescription;
    private LocalDateTime createAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentStatus status = RecruitmentStatus.ARCHIVED;

    @OneToOne
    @JoinColumn
    private RecruitmentRequirements recruitmentRequirements;

    @ManyToOne
    @JoinColumn(name = "on_approve")
    @JsonBackReference
    private Employees employees;

    public Recruitment(Recruitment recruitment) {
        this.id = IdGenerator.getGenerationId();
        this.position = recruitment.getPosition();
        this.contactPhone = recruitment.getContactPhone();
        this.email = recruitment.getEmail();
        this.deadline = recruitment.getDeadline();
        this.createAt = LocalDateTime.now();
        this.status = RecruitmentStatus.ARCHIVED;
        this.jobDescription = recruitment.getJobDescription();
        this.recruitmentRequirements = recruitment.getRecruitmentRequirements();
        this.employees = recruitment.getEmployees();
    }
}
