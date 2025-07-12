package com.project.hrm.training.dto.trainingEnrollmentDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingEnrollmentUpdateDTO {

    @NotNull(message = "Enrollment ID is required")
    private Integer id;

    private LocalDateTime enrollmentDate;

    private LocalDateTime completionDate;

    @DecimalMin(value = "0.0", inclusive = true, message = "Attendance rate must be 0 or greater")
    @DecimalMax(value = "100.0", message = "Attendance rate must not exceed 100")
    private Double attendanceRate;

    @DecimalMin(value = "0.0", inclusive = true, message = "Test score must be 0 or greater")
    @DecimalMax(value = "100.0", message = "Test score must not exceed 100")
    private Double testScore;

    @Size(max = 1000, message = "Feedback must not exceed 1000 characters")
    private String feedback;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "ENROLLED|IN_PROGRESS|COMPLETED|DROPPED|FAILED",
            message = "Status must be one of: ENROLLED, COMPLETED, DROPPED, FAILED"
    )
    private String status;
}
