package com.project.hrm.dto.salaryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.project.hrm.dto.employeeDTO.EmployeeDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO {
    private Integer id;
    private LocalDateTime time;
    private EmployeeDTO employeeDTO;
}
