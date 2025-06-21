package com.project.hrm.dto.approvalDTO;

import com.project.hrm.enums.PayrollStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDTO {
    private Integer id;
    private Integer employeeReviewId;
    private Integer payrollId;
    private LocalDateTime approvalDate;
    private String comment;
    private PayrollStatus status;
}
