package com.project.hrm.enums;

import java.math.BigDecimal;

public enum ScaleOfTaxation {
    LEVEL1(0, 5000000, 0.05),
    LEVEL2(5000000, 10000000, 0.10),
    LEVEL3(10000000, 18000000, 0.15),
    LEVEL4(18000000, 32000000, 0.20),
    LEVEL5(32000000, 52000000, 0.25),
    LEVEL6(52000000, 80000000, 0.30),
    LEVEL7(80000000, Double.MAX_VALUE, 0.35);

    private final double belowLevel;
    private final double aboveLevel;
    private final double taxRate;


    ScaleOfTaxation(double belowLevel, double aboveLevel, double taxRate) {
        this.belowLevel = belowLevel;
        this.aboveLevel = aboveLevel;
        this.taxRate = taxRate;
    }

    public BigDecimal taxCalculation(BigDecimal taxableIncome) {
        if (taxableIncome.compareTo(BigDecimal.valueOf(belowLevel)) <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal min = taxableIncome.min(BigDecimal.valueOf(aboveLevel));

        BigDecimal taxMoney = min.subtract(BigDecimal.valueOf(belowLevel));

        return taxMoney.multiply(BigDecimal.valueOf(taxRate));
    }

}
