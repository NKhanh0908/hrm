package com.project.hrm.dto.salaryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubsidyDTO {
    private Integer id;
    private String typeSubsidy;
    private Double amount;
    private SalaryDTO salaryDTO;
}
