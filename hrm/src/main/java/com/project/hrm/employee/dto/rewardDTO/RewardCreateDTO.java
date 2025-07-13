package com.project.hrm.employee.dto.rewardDTO;

import jakarta.validation.constraints.*;
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
public class RewardCreateDTO {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Reason cannot be blank")
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;

    private BigDecimal rewardAmount;

    private Boolean isPercentage;
    private Float percentage;

    @NotNull(message = "Reward date cannot be null")
    @PastOrPresent(message = "Reward date must be today or in the past")
    private LocalDateTime rewardDate;

    private Boolean appliedToPayroll = false;

    @NotNull(message = "Employee ID cannot be null")
    @Positive(message = "Employee ID must be a positive number")
    private Integer employeeId;
}
