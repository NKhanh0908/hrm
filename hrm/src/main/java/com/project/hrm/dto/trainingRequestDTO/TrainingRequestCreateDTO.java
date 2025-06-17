package com.project.hrm.dto.trainingRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequestCreateDTO {
    private String reason;
    private String expectedOutcome;
    private LocalDateTime requestDate;
    private LocalDateTime approvedDate;
    private String priority;
    private Integer targetEmployeeId;
    private Integer requestedProgramId;
}
