package com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO;

import lombok.Data;

@Data
public class PerformanceReviewDetailFilter {
    private Integer reviewerId;
    private Integer performanceReviewId;
    private String criteriaName;
}