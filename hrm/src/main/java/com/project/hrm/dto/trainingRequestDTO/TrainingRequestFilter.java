package com.project.hrm.dto.trainingRequestDTO;

import com.project.hrm.enums.TrainingRequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrainingRequestFilter {
    private LocalDateTime requestDate;
    private String status;
    private Integer targetEmployeeId;
    private Integer employeeRequestId;
    private Integer employeeApprovedId;
    private Integer requestedProgramId;
}
