package com.project.hrm.training.dto.trainingEnrollmentDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TrainingEnrollmentFilter {
    @Schema(description = "ID of the training session", example = "1", nullable = true)
    private Integer trainingSessionId;

    @Schema(description = "ID of the enrolled employee", example = "15", nullable = true)
    private Integer employeeId;
}
