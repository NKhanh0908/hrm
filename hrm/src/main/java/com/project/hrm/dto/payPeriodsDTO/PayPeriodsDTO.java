package com.project.hrm.dto.payPeriodsDTO;

import com.project.hrm.enums.PayPeriodStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayPeriodsDTO {
    private Integer id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String payPeriodCode;
    private PayPeriodStatus status;
}
