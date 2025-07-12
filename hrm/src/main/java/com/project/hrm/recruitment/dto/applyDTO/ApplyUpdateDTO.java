package com.project.hrm.recruitment.dto.applyDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class ApplyUpdateDTO {

    @NotNull(message = "Apply ID is required")
    private Integer id;

    @NotBlank(message = "Application status is required")
    @Pattern(
            regexp = "SUBMITTED|INTERVIEW|REJECTED|HIRED",
            message = "Status must be one of: SUBMITTED, INTERVIEW, REJECTED, HIRED"
    )
    private String status;

    @NotBlank(message = "Position is required")
    @Size(max = 100, message = "Position must not exceed 100 characters")
    private String position;

    @NotNull(message = "Recruitment ID is required")
    private Integer recruitmentId;

    @NotNull(message = "Candidate profile ID is required")
    private Integer candidateProfileId;
}
