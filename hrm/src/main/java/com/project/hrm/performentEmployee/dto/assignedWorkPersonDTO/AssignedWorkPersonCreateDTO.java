package com.project.hrm.performentEmployee.dto.assignedWorkPersonDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AssignedWorkPersonCreateDTO {

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

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;
}
