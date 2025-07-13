package com.project.hrm.payroll.dto.payrollsDTO;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Minimum total income", example = "10000000.00", nullable = true)
    private BigDecimal minIncome;

    @Schema(description = "Maximum total income", example = "30000000.00", nullable = true)
    private BigDecimal maxIncome;

    @Schema(description = "Minimum total deduction", example = "1000000.00", nullable = true)
    private BigDecimal minDeduction;

    @Schema(description = "Maximum total deduction", example = "5000000.00", nullable = true)
    private BigDecimal maxDeduction;

    @Schema(description = "Minimum net salary", example = "8000000.00", nullable = true)
    private BigDecimal minNetSalary;

    @Schema(description = "Maximum net salary", example = "25000000.00", nullable = true)
    private BigDecimal maxNetSalary;
}
