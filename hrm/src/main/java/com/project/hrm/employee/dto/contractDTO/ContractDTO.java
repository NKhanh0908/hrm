package com.project.hrm.employee.dto.contractDTO;

import com.project.hrm.employee.dto.employeeDTO.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Integer id;
    private String title;
    private LocalDateTime contractSigningDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double baseSalary;
    private String description;
    private String status;
    private EmployeeDTO employee;
    private Integer departmentId;
    private String departmentName;
    private String roleName;
}
