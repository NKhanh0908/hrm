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
public class PayrollComponentsFilter {
    @Schema(description = "Component name (e.g. Bonus, Tax)", example = "Bonus", nullable = true)
    private String name;

    @Schema(
            description = "Type of payroll component (FIXED, VARIABLE, ALLOWANCE, DEDUCTION, etc.)",
            example = "FIXED",
            nullable = true,
            implementation = PayrollComponentType.class
    )
    private PayrollComponentType type;

    @Schema(description = "Exact amount of the component", example = "500000.00", nullable = true)
    private BigDecimal amount;

    @Schema(description = "Whether this component is a percentage of salary", example = "true",implementation = Boolean.class, nullable = true)
    private Boolean isPercentage;

    @Schema(description = "Percentage value (if isPercentage is true)", example = "10.5", nullable = true)
    private Float percentage;

    @Schema(description = "Related regulation ID", example = "3", nullable = true)
    private Integer regulationId;

    @Schema(description = "Related payroll ID", example = "7", nullable = true)
    private Integer payrollsId;
}
