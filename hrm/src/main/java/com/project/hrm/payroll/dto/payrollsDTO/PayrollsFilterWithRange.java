package com.project.hrm.payroll.dto.payrollsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayrollsFilterWithRange {
    private BigDecimal minIncome;
    private BigDecimal maxIncome;
    private BigDecimal minDeduction;
    private BigDecimal maxDeduction;
    private BigDecimal minNetSalary;
    private BigDecimal maxNetSalary;
}
