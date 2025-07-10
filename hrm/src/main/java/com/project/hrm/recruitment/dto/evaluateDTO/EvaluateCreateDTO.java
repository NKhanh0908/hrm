package com.project.hrm.recruitment.dto.evaluateDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateCreateDTO {
    private String feedback;
    private LocalDateTime feedbackAt;
    private Integer candidateProfileId;
    private Integer createBy;
    private String evaluate;
}
