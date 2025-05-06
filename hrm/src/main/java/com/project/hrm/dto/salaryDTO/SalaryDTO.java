package com.project.hrm.dto.salaryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO {
    private Integer id;
    private LocalDateTime time;
    private Double totalAmount;
    //private EmployeeDTO employeeDTO;
    private DetailSalaryDTO detailSalaryDTO;
    private List<SubsidyDTO> subsidyDTOList;
}
