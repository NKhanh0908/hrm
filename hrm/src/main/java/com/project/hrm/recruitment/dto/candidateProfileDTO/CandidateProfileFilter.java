package com.project.hrm.recruitment.dto.candidateProfileDTO;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CandidateProfileFilter {
    @Schema(description = "Candidate's full name or part of it", example = "Alice", nullable = true)
    private String name;

    @Schema(description = "Candidate's email (contains)", example = "alice@example.com", nullable = true)
    private String email;

    @Schema(description = "Candidate's phone number", example = "0912345678", nullable = true)
    private String phone;

    @Schema(description = "Candidate's position", example = "Hr", nullable = true)
    private String position;

    @Schema(description = "Related Recruitment ID", example = "5", nullable = true)
    private Integer recruitmentId;
}

