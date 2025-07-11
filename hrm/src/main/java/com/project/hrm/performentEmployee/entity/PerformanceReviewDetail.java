package com.project.hrm.performentEmployee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.employee.entity.Employees;
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
public class PerformanceReviewDetail {
    @Id
    private Integer id;
    private String criteriaName;
    private String criteriaDescription;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employees reviewer;

    private String comment;
    private Float score;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private PerformanceReview performanceReview;

    private LocalDateTime reviewDate;
}
