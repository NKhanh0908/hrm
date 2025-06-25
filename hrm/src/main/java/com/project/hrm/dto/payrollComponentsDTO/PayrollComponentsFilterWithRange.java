package com.project.hrm.dto.payrollComponentsDTO;

import com.project.hrm.enums.PayrollComponentType;
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
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Float minPercentage;
    private Float maxPercentage;
    private PayrollComponentType type;
}
