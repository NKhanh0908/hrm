package com.project.hrm.recruitment.dto.recruitmentRequirementDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecruitmentRequirementFilter {
    private String positions;
    private String status;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Integer departmentId;
    private Integer roleId;
}
