package com.project.hrm.training.dto.trainingRequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequestCreateDTO {

    @NotBlank(message = "Reason is required")
    @Size(max = 1000, message = "Reason must not exceed 1000 characters")
    private String reason;

    @NotBlank(message = "Expected outcome is required")
    @Size(max = 1000, message = "Expected outcome must not exceed 1000 characters")
    private String expectedOutcome;

    @NotNull(message = "Request date is required")
    private LocalDateTime requestDate;

    private LocalDateTime approvedDate;

    @NotBlank(message = "Priority is required")
    @Pattern(
            regexp = "LOW|MEDIUM|HIGH|URGENT",
            message = "Priority must be one of: LOW, MEDIUM, HIGH, URGENT"
    )
    private String priority;

    @NotNull(message = "Target employee ID is required")
    private Integer targetEmployeeId;

    @NotNull(message = "Requested training program ID is required")
    private Integer requestedProgramId;
}
