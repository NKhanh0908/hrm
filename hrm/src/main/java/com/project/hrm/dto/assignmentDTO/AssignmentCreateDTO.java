package com.project.hrm.dto.assignmentDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentCreateDTO {
    @NotNull (message = "Employee id is required")
    private Integer employeeId;

    @NotNull (message = "Department id is required")
    private Integer departmentId;

    @NotNull (message = "Role id is required")
    private Integer roleId;
}
