package com.project.hrm.dto.trainingRequestDTO;

import com.project.hrm.enums.TrainingRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequestDTO {
    private Integer id;
    private String reason;
    private String expectedOutcome;
    private LocalDateTime requestDate;
    private LocalDateTime approvedDate;
    private String priority;
    private TrainingRequestStatus status;
    private Integer targetEmployeeId;
    private String targetEmployeeName;
    private Integer employeeRequestId;
    private String employeeRequestName;
    private Integer employeeApprovedId;
    private String employeeApprovedName;
    private Integer requestedProgramId;
    private String requestedProgramName;
}
