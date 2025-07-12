package com.project.hrm.performentEmployee.entity;

import com.project.hrm.employee.entity.Employees;
import com.project.hrm.performentEmployee.enums.PerformanceReviewStatus;
import com.project.hrm.performentEmployee.enums.ReviewCycle;
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
public class PerformanceReview {
    @Id
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime reviewStartDate;
    private LocalDateTime reviewEndDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerformanceReviewStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewCycle reviewCycle;

    private String overallComment;
    private Float overallScore;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employees employee;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employees employeeRequest;

    @ManyToOne
    @JoinColumn
    private Employees approver;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
