package com.project.hrm.performentEmployee.dto.performanceReviewDTO;

import com.project.hrm.performentEmployee.enums.PerformanceReviewStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PerformanceReviewFilter {
    @Schema(description = "ID of the employee being reviewed", example = "123", nullable = true)
    private Integer employeeId;

    @Schema(description = "Employee request ID related to this review", example = "456", nullable = true)
    private Integer employeeRequestId;

    @Schema(description = "ID of the approver", example = "789", nullable = true)
    private String approverId;

    @Schema(description = "Start datetime of created reviews", example = "2024-01-01T00:00:00", nullable = true)
    private LocalDateTime createdAtFrom;

    @Schema(description = "End datetime of created reviews", example = "2024-12-31T23:59:59", nullable = true)
    private LocalDateTime createdAtTo;

    @Schema(
            description = "Status of the performance review",
            example = "APPROVED",
            nullable = true,
            implementation = PerformanceReviewStatus.class
    )
    private PerformanceReviewStatus status;

    @Schema(description = "Review cycle label or code", example = "Q1-2024", nullable = true)
    private String reviewCycle;
}
