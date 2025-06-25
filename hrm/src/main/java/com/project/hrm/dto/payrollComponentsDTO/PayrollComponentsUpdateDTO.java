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
public class PayrollComponentsUpdateDTO {
    private Integer id;
    private String name;
    private PayrollComponentType type;
    private BigDecimal amount;
    private Boolean isPercentage;
    private Float percentage;
    private Integer regulationId;
    private Integer payrollsId;
}
