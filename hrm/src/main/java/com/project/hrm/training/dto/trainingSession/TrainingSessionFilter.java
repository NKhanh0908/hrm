package com.project.hrm.training.dto.trainingSession;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TrainingSessionFilter {
    @Schema(description = "Training session name (full or partial match)", example = "Leadership Training", nullable = true)
    private String sessionName;

    @Schema(description = "ID of the associated training program", example = "5", nullable = true)
    private Integer trainingProgramId;

    @Schema(description = "ID of the employee coordinating the session", example = "12", nullable = true)
    private Integer coordinatorId;
}
