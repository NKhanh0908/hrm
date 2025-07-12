package com.project.hrm.performentEmployee.entity;

import com.project.hrm.employee.entity.Employees;
import com.project.hrm.performentEmployee.enums.ReviewType;
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
public class FeedbackEmployee {
    @Id
    private Integer id;
    private String feedbackContent;
    private String strengths;
    private String areasForImprovement;
    private String suggestions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewType feedbackType;

    private Boolean isAnonymous;

    @ManyToOne
    @JoinColumn(name = "feedback_provider_id")
    private Employees feedbackProvider;

    @ManyToOne
    @JoinColumn(name = "performance_review_id")
    private PerformanceReview performanceReview;

    private LocalDateTime createdAt;
}