package com.project.hrm.dto.payrollDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayrollDetailCreateDTO {
    private BigDecimal amount;
    private Boolean isPercentage;
    private Float percentage;
    private Integer payrollId;
    private Integer payrollComponentId;
}
