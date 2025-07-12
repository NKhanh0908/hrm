package com.project.hrm.department.dto.roleDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateDTO {

    @NotBlank(message = "Role name is required")
    private String roleName;

    @NotBlank(message = "Department id is required")
    private Integer departmentId;
}
