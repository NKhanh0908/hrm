package com.project.hrm.payrollManager.dto.regulationsDTO;

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
public class RegulationsCreateDTO {
    private String regulationKey;
    private String name;
    private BigDecimal amount;
    private Float percentage;
    private BigDecimal applicableSalary;
    private LocalDateTime effectiveDate;
}
