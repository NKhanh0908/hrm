package com.project.hrm.dto.recruitmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentRequirementFilter {
    private String positions;
    private String status;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Integer departmentId;
}
