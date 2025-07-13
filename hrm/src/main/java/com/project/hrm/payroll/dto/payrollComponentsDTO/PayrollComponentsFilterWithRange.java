package com.project.hrm.payroll.dto.payrollComponentsDTO;

import com.project.hrm.payroll.enums.PayrollComponentType;
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
public class PayrollComponentsFilterWithRange {
    @Schema(description = "Minimum component amount", example = "100000.00", nullable = true)
    private BigDecimal minAmount;

    @Schema(description = "Maximum component amount", example = "2000000.00", nullable = true)
    private BigDecimal maxAmount;

    @Schema(description = "Minimum percentage", example = "5.0", nullable = true)
    private Float minPercentage;

    @Schema(description = "Maximum percentage", example = "20.0", nullable = true)
    private Float maxPercentage;

    @Schema(
            description = "Type of payroll component (e.g. FIXED, VARIABLE, ALLOWANCE, DEDUCTION)",
            example = "ALLOWANCE",
            nullable = true,
            implementation = PayrollComponentType.class
    )
    private PayrollComponentType type;
}
