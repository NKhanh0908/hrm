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
public class RegulationsDTO {
    private Integer id;
    private String name;
    private PayrollComponentType type;
    private BigDecimal amount;
    private Float percentage;
    private BigDecimal applicableSalary;
    private LocalDateTime effectiveDate;
}
