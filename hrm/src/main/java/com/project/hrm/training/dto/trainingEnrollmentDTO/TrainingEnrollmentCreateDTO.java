package com.project.hrm.training.dto.trainingEnrollmentDTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingEnrollmentCreateDTO {

    private Double attendanceRate;
    private Double testScore;
    private String feedback;

    @NotNull(message = "Training session ID is required")
    private Integer trainingSessionId;

    @NotNull(message = "Training request ID is required")
    private Integer trainingRequestId;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;
}
