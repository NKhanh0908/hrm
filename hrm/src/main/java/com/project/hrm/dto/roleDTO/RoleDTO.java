package com.project.hrm.dto.roleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Integer id;
    private String roleName;
    private Integer departmentId;
    private String departmentName;
}
