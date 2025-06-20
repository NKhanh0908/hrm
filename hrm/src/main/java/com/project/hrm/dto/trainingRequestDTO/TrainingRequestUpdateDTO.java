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
public class TrainingRequestUpdateDTO {
    private Integer id;
    private String reason;
    private String expectedOutcome;
    private String priority;
    private String status;
    private Integer targetEmployeeId;
    private Integer requestedProgramId;
}
