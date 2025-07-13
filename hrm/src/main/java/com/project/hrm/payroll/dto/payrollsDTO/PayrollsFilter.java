package com.project.hrm.payroll.dto.payrollsDTO;

import com.project.hrm.payroll.enums.PayrollStatus;
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
public class PayrollsFilter {
    @Schema(description = "ID of the employee", example = "101", nullable = true)
    private Integer employeeId;

    @Schema(description = "Pay period ID", example = "5", nullable = true)
    private Integer payPeriodId;

    @Schema(description = "Total income", example = "15000000.00", nullable = true)
    private BigDecimal totalIncome;

    @Schema(description = "Total deduction", example = "2000000.00", nullable = true)
    private BigDecimal totalDeduction;

    @Schema(description = "Net salary", example = "13000000.00", nullable = true)
    private BigDecimal netSalary;

    @Schema(
            description = "Payroll status (PENDING, APPROVED, REJECTED, PAID)",
            example = "APPROVED",
            nullable = true,
            implementation = PayrollStatus.class
    )
    private PayrollStatus status;
}
