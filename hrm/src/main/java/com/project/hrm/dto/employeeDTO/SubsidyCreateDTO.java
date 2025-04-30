package com.project.hrm.dto.employeeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubsidyCreateDTO {
    private String typeSubsidy;
    private Double amount;
    private Integer salaryId;
}
