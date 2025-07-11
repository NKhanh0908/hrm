package com.project.hrm.payroll.dto.regulationsDTO;

import com.project.hrm.payroll.enums.PayrollComponentType;
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
    private String name;
    private BigDecimal applicableSalary;
    private LocalDateTime effectiveDate;
    private PayrollComponentType payrollComponentType;
}
