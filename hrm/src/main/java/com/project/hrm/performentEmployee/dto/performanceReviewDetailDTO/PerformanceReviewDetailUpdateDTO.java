package com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReviewDetailUpdateDTO {
    private Integer id;
    private String criteriaName;
    private String criteriaDescription;
    private Integer reviewerId;
    private String comment;
    private Float score;
    private Integer performanceReviewId;
}