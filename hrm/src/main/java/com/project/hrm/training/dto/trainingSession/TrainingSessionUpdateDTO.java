package com.project.hrm.training.dto.trainingSession;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingSessionUpdateDTO {

    @NotNull(message = "Training session ID is required")
    private Integer id;

    @NotBlank(message = "Session name is required")
    @Size(max = 255, message = "Session name must not exceed 255 characters")
    private String sessionName;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 hour")
    private Integer durationHours;

    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Cost must be 0 or greater")
    private Double cost;

    @NotBlank(message = "Location is required")
    @Size(max = 255, message = "Location must not exceed 255 characters")
    private String location;

    @NotNull(message = "Maximum number of participants is required")
    @Min(value = 1, message = "There must be at least 1 participant allowed")
    private Integer maxParticipants;

    @NotNull(message = "Current number of participants is required")
    @Min(value = 0, message = "Current participants cannot be negative")
    private Integer currentParticipants;

    @NotNull(message = "Training program ID is required")
    private Integer trainingProgramId;

    private Integer coordinatorId;
}
