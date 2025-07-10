package com.project.hrm.recruitment.dto.recruitmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentUpdateDTO {
    private Integer id;
    private String contactPhone;
    private String email;
    private LocalDateTime deadline;
    private String jobDescription;
    private Integer recruitmentRequirementId;
}
