package com.project.hrm.recruitment.dto.evaluateDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EvaluateFilter{
    @Schema(description = "Candidate ID to filter evaluations", example = "12", nullable = true)
    private Integer candidateId;

    @Schema(description = "Evaluator's employee ID", example = "8", nullable = true)
    private Integer employeeId;
}
