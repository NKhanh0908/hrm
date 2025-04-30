package com.project.hrm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    private String position;
    private String contactPhone;
    private String email;
    private LocalDateTime deadline;
    private String jobDescription;
    private LocalDateTime createAt;

    @OneToOne
    @JoinColumn
    private RecruitmentRequirements recruitmentRequirements;
}
