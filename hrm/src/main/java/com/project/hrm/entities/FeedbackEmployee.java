package com.project.hrm.entities;

import com.project.hrm.enums.ReviewType;
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
    @JoinColumn(name = "employee_id", nullable = false)
    private Employees employee;

    @ManyToOne
    @JoinColumn(name = "feedback_provider_id")
    private Employees feedbackProvider;

    @ManyToOne
    @JoinColumn(name = "performance_review_id")
    private PerformanceReview performanceReview;

    private LocalDateTime createdAt;
}