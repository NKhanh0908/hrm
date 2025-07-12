package com.project.hrm.performentEmployee.dto.assignedWorkPersonDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignedWorkPersonUpdateDTO {

    @NotNull(message = "Assigned task ID is required")
    private Integer id;

    @NotBlank(message = "Task title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "Target date is required")
    private LocalDateTime targetDate;

    private LocalDateTime completedDate;

    @Min(value = 0, message = "Progress must be at least 0%")
    @Max(value = 100, message = "Progress must not exceed 100%")
    private Integer progressPercentage;

    private String progressNotes;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;
}
