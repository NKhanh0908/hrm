package com.project.hrm.payrollManager.dto.approvalsDTO;

import com.project.hrm.payrollManager.enums.PayrollStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalsDTO {
    private Integer id;
    private Integer employeeReviewId;
    private Integer payrollId;
    private LocalDateTime approvalDate;
    private String comment;
    private PayrollStatus status;
}
