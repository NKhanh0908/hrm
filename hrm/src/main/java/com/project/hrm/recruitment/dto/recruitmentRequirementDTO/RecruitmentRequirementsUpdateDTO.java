package com.project.hrm.recruitment.dto.recruitmentRequirementDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentRequirementsUpdateDTO {
    private Integer id;
    private String description;
    private Integer quantity;
    private String expectedSalary;
    private String status;
    private Integer roleId;
}
