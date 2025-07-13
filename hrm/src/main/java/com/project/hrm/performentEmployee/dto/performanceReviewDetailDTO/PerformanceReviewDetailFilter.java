package com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PerformanceReviewDetailFilter {
    @Schema(description = "ID of the reviewer", example = "101", nullable = true)
    private Integer reviewerId;

    @Schema(description = "ID of the related performance review", example = "202", nullable = true)
    private Integer performanceReviewId;

    @Schema(description = "Name or keyword of the review criteria", example = "Teamwork", nullable = true)
    private String criteriaName;
}