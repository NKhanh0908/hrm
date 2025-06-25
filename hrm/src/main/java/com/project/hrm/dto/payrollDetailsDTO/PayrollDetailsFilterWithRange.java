package com.project.hrm.dto.payrollDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayrollDetailsFilterWithRange {
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Float minPercentage;
    private Float maxPercentage;
}
