package com.project.hrm.dto.candidateProfileDTO;

import com.project.hrm.dto.evaluateDTO.EvaluateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
