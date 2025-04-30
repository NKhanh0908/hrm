package com.project.hrm.dto.employeeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailSalaryCreateDTO {
    private Double basicSalary;
    private Integer salaryId;
}
