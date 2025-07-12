package com.project.hrm.recruitment.dto.recruitmentRequirementDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentRequirementsCreateDTO {
    private String description;
    private Integer quantity;
    private String expectedSalary;
    private Integer roleId;
}
