package com.project.hrm.payroll.enums;

import java.math.BigDecimal;

public enum ScaleOfTaxation {
    LEVEL1(BigDecimal.ZERO, new BigDecimal("5000000"), new BigDecimal("0.05")),
    LEVEL2(new BigDecimal("5000000"), new BigDecimal("10000000"), new BigDecimal("0.10")),
    LEVEL3(new BigDecimal("10000000"), new BigDecimal("18000000"), new BigDecimal("0.15")),
    LEVEL4(new BigDecimal("18000000"), new BigDecimal("32000000"), new BigDecimal("0.20")),
    LEVEL5(new BigDecimal("32000000"), new BigDecimal("52000000"), new BigDecimal("0.25")),
    LEVEL6(new BigDecimal("52000000"), new BigDecimal("80000000"), new BigDecimal("0.30")),
    LEVEL7(new BigDecimal("80000000"), new BigDecimal(Double.MAX_VALUE), new BigDecimal("0.35")); // Thay Double.MAX_VALUE bằng giá trị thực tế

    private final BigDecimal belowLevel;
    private final BigDecimal aboveLevel;
    private final BigDecimal taxRate;

    ScaleOfTaxation(BigDecimal belowLevel, BigDecimal aboveLevel, BigDecimal taxRate) {
        this.belowLevel = belowLevel;
        this.aboveLevel = aboveLevel;
        this.taxRate = taxRate;
    }

    public BigDecimal calculateTax(BigDecimal taxableIncome) {
        if (taxableIncome == null || taxableIncome.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        for (ScaleOfTaxation level : values()) {
            if (taxableIncome.compareTo(level.belowLevel) > 0) {
                BigDecimal taxableAmount = taxableIncome.min(level.aboveLevel)
                        .subtract(level.belowLevel);
                if (taxableAmount.compareTo(BigDecimal.ZERO) > 0) {
                    return taxableAmount.multiply(level.taxRate);
                }
            }
        }
        return BigDecimal.ZERO;
    }
}