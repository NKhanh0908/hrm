package com.project.hrm.dto.roleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateDTO {
    private String roleName;
    private Integer departmentId;
}
