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
public class EvaluateUpdateDTO {
    private Integer id;
    private String feedback;
    private LocalDateTime feedbackAt;
    private Integer candidateId;
    private Integer createBy;
    private String evaluate;
}
