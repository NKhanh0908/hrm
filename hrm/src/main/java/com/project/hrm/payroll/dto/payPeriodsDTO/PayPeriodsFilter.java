package com.project.hrm.payroll.dto.payPeriodsDTO;

import com.project.hrm.payroll.enums.PayPeriodStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayPeriodsFilter {
    @Schema(description = "Start date of the pay period", example = "2025-07-01T00:00:00", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @Schema(description = "End date of the pay period", example = "2025-07-31T23:59:59", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @Schema(description = "Pay period code for filtering", example = "PP202507", nullable = true)
    private String payPeriodCode;

    @Schema(
            description = "Status of the pay period (e.g. OPEN, CLOSED, APPROVED)",
            example = "OPEN",
            nullable = true,
            implementation = PayPeriodStatus.class
    )
    private PayPeriodStatus status;
}
