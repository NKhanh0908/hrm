package com.project.hrm.recruitment.dto.candidateProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfileDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String linkCV;
    private String skills;
    private String experience;
    private LocalDateTime createProfileAt;
}
