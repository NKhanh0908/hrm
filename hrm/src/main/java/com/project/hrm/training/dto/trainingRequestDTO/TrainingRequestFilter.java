package com.project.hrm.training.dto.trainingRequestDTO;

import com.project.hrm.training.enums.TrainingRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrainingRequestFilter {
    @Schema(description = "Date the training request was made", example = "2025-06-01T08:30:00", nullable = true)
    private LocalDateTime requestDate;

    @Schema(description = "Status of the training request", example = "PENDING", implementation = TrainingRequestStatus.class)
    private TrainingRequestStatus status;

    @Schema(description = "ID of the target employee for training", example = "10", nullable = true)
    private Integer targetEmployeeId;

    @Schema(description = "ID of the employee who made the request", example = "5", nullable = true)
    private Integer employeeRequestId;

    @Schema(description = "ID of the employee who approved the request", example = "3", nullable = true)
    private Integer employeeApprovedId;

    @Schema(description = "ID of the requested training program", example = "7", nullable = true)
    private Integer requestedProgramId;
}
