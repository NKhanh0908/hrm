package com.project.hrm.recruitment.dto.applyDTO;

import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDTO {
    private Integer id;
    private LocalDateTime applyAt;
    private String status;
    private String position;
    private RecruitmentDTO recruitmentDTO;
    private CandidateProfileDTO candidateProfileDTO;
}
