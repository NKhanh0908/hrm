package com.project.hrm.payroll.dto.payPeriodsDTO;

import com.project.hrm.payroll.enums.PayPeriodStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayPeriodsUpdateDTO {
    private Integer id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String payPeriodCode;
    private PayPeriodStatus status;
}
