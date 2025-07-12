package com.project.hrm.recruitment.dto.applyDTO;

import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyCreateDTO {

    @NotBlank(message = "Position must not be blank")
    private String position;

    @NotNull(message = "Recruitment ID is required")
    private Integer recruitmentId;

    @NotNull(message = "Candidate Profile is required")
    private CandidateProfileCreateDTO candidateProfileCreateDTO;
}
