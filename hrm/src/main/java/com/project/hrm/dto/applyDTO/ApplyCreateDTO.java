package com.project.hrm.dto.applyDTO;

import jakarta.validation.constraints.NotBlank;
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
public class ApplyCreateDTO {
    private LocalDateTime applyAt;

    private String status;

    @NotBlank(message = "Position must not be blank")
    private String position;

    @NotNull(message = "Recruitment ID is required")
    private Integer recruitmentId;

    @NotNull(message = "Candidate Profile ID is required")
    private Integer candidateProfileId;
}
