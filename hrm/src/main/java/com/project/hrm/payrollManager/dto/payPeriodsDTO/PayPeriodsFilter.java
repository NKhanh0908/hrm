package com.project.hrm.payrollManager.dto.payPeriodsDTO;

import com.project.hrm.payrollManager.enums.PayPeriodStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayPeriodsFilter {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String payPeriodCode;
    private PayPeriodStatus status;
}
