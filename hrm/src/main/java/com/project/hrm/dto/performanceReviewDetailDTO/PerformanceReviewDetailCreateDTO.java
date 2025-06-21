package com.project.hrm.dto.performanceReviewDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReviewDetailCreateDTO {
    private String criteriaName;
    private String criteriaDescription;
    private Integer reviewerId;
    private String comment;
    private Float score;
    private Integer performanceReviewId;
}