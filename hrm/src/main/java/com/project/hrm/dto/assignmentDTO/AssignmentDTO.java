package com.project.hrm.dto.assignmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {
    private Integer id;
    private String employeeName;
    private String departmentName;
    private String roleName;
}
