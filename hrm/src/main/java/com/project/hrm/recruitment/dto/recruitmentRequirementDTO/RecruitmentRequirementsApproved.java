package com.project.hrm.recruitment.dto.recruitmentRequirementDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentRequirementsApproved {
    private Integer recruitmentRequirementId;
    private String contactPhone;
    private String email;
    private LocalDateTime deadline;
    private String jobDescription;
}
