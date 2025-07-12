package com.project.hrm.recruitment.dto.recruitmentRequirementDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentRequirementsDTO {
    private Integer id;
    private String description;
    private Integer quantity;
    private String expectedSalary;
    private String status;
    private LocalDateTime dateRequired;
    private String roleName;
    private Integer departmentId;
    private String departmentName;
    private Integer employeeId;
    private String employeeName;
}
