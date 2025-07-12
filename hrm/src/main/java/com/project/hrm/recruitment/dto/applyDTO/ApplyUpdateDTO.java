package com.project.hrm.recruitment.dto.applyDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyUpdateDTO {
    @NotNull(message = "Apply ID is required")
    private Integer id;
    private LocalDateTime applyAt;
    private String status;
    private String position;
    private Integer recruitmentId;
    private Integer candidateProfileId;
}
