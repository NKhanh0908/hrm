package com.project.hrm.dto.recruitmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentRequirementsCreateDTO {
    private String description;
    private String positions;
    private Integer quantity;
    private String expectedSalary;
    private String status;
    private Integer departmentId;
    private Integer createdBy;
}
