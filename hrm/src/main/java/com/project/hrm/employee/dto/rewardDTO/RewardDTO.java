package com.project.hrm.employee.dto.rewardDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RewardDTO {
    private Integer id;

    private String title;

    private String reason;

    private BigDecimal rewardAmount;

    private Boolean isPercentage;
    private Float percentage;

    private LocalDateTime rewardDate;

    private Boolean appliedToPayroll;

    private Integer employeeId;
}
