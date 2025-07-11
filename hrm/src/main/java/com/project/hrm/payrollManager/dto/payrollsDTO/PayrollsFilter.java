package com.project.hrm.payrollManager.dto.payrollsDTO;

import com.project.hrm.payrollManager.enums.PayrollStatus;
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
    private Integer employeeId;
    private Integer payPeriodId;
    private BigDecimal totalIncome;
    private BigDecimal totalDeduction;
    private BigDecimal netSalary;
    private PayrollStatus status;
}
