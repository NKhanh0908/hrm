package com.project.hrm.payroll.dto.approvalsDTO;

import com.project.hrm.payroll.enums.PayrollStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalsFilter {
    private Integer employeeReviewId;
    private Integer payrollId;
    private LocalDateTime approvalDate;
    private String comment;
    private PayrollStatus status;
}
