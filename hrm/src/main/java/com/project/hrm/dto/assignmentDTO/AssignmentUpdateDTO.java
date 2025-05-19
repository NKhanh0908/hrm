package com.project.hrm.dto.assignmentDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentUpdateDTO {
    @NotNull(message = "Assignment id is required")
    private Integer id;

    @NotNull(message = "Employee id is required")
    private Integer employeeId;

    @NotNull (message = "Department id is required")
    private Integer departmentId;

    @NotNull (message = "Role id is required")
    private Integer roleId;
}
