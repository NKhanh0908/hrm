package com.project.hrm.recruitment.entity;

import com.project.hrm.employee.entity.Employees;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Evaluate {
    @Id
    private Integer id;
    private String feedback;
    private LocalDateTime feedbackAt;
    private String evaluate;

    @ManyToOne
    @JoinColumn
    private CandidateProfile candidateProfile;

    @ManyToOne
    @JoinColumn
    private Employees createBy;

}