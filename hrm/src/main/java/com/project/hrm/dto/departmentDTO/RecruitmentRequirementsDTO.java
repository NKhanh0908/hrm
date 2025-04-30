package com.project.hrm.dto.departmentDTO;

import com.project.erp.dto.hrm.employeeDTO.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentRequirementsDTO {
    private Integer id;
    private String description;
    private String positions;
    private Integer quantity;
    private String expectedSalary;
    private String status;
    private LocalDateTime dateRequired;
    private DepartmentDTO departmentDTO;
    private EmployeeDTO employeeDTO;
}
