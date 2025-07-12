package com.project.hrm.recruitment.dto.evaluateDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Evaluation ID is required")
    private Integer id;

    @Size(max = 1000, message = "Feedback must not exceed 1000 characters")
    private String feedback;

    @NotNull(message = "Candidate profile ID is required")
    private Integer candidateId;

    @NotBlank(message = "Evaluation content is required")
    @Size(max = 1000, message = "Evaluation must not exceed 1000 characters")
    private String evaluate;
}
