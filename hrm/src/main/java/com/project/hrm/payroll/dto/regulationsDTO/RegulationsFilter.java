package com.project.hrm.payroll.dto.regulationsDTO;

import com.project.hrm.payroll.enums.PayrollComponentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegulationsFilter {
    @Schema(description = "Regulation name or keyword", example = "Overtime Pay Rule", nullable = true)
    private String name;

    @Schema(description = "Applicable salary threshold", example = "15000000.00", nullable = true)
    private BigDecimal applicableSalary;

    @Schema(description = "Effective date of the regulation", example = "2024-01-01T00:00:00", nullable = true)
    private LocalDateTime effectiveDate;

    @Schema(
            description = "Type of payroll component (e.g. FIXED, VARIABLE, ALLOWANCE, DEDUCTION)",
            example = "ALLOWANCE",
            nullable = true,
            implementation = PayrollComponentType.class
    )
    private PayrollComponentType payrollComponentType;
}
