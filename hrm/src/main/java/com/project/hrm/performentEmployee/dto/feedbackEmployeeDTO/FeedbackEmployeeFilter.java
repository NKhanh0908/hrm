package com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEmployeeFilter {
    @Schema(description = "ID of the employee receiving feedback", example = "101", nullable = true)
    private Integer employeeId;

    @Schema(description = "ID of the person providing feedback", example = "202", nullable = true)
    private Integer feedbackProviderId;

    @Schema(description = "ID of the related performance review", example = "301", nullable = true)
    private Integer performanceReviewId;

    @Schema(description = "Start datetime for filtering by creation time", example = "2024-01-01T00:00:00", nullable = true)
    private LocalDateTime fromCreateAt;

    @Schema(description = "End datetime for filtering by creation time", example = "2024-12-31T23:59:59", nullable = true)
    private LocalDateTime toCreateAt;
}
