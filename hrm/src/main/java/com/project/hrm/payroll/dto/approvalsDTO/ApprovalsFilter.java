package com.project.hrm.payroll.dto.approvalsDTO;

import com.project.hrm.payroll.enums.PayrollStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalsFilter {
    @Schema(description = "ID of the employee who reviewed", example = "12", nullable = true)
    private Integer employeeReviewId;

    @Schema(description = "Payroll ID", example = "8", nullable = true)
    private Integer payrollId;

    @Schema(description = "Date of approval", example = "2025-07-01T10:00:00", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime approvalDate;

    @Schema(description = "Comment for the approval", example = "Approved with note", nullable = true)
    private String comment;

    @Schema(
            description = "Payroll status (PENDING, APPROVED, REJECTED, PAID)",
            example = "APPROVED",
            nullable = true,
            implementation = PayrollStatus.class
    )
    private PayrollStatus status;
}
