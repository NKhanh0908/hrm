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
public class RecruitmentFilter {
    private LocalDateTime deadlineFrom;
    private LocalDateTime deadlineTo;
    private LocalDateTime dateCreateFrom;
    private LocalDateTime dateCreateTo;
    private Integer recruitmentRequirementID;
    private String status;
}
