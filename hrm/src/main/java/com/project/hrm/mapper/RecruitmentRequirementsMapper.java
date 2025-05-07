package com.project.hrm.mapper;

import com.project.hrm.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsDTO;
import com.project.hrm.entities.Recruitment;
import com.project.hrm.entities.RecruitmentRequirements;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecruitmentRequirementsMapper {

    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper employeeMapper;


    public RecruitmentRequirementsDTO toRecruitmentRequirementsDTO(RecruitmentRequirements recruitmentRequirements) {
        return RecruitmentRequirementsDTO.builder()
                .id(recruitmentRequirements.getId())
                .dateRequired(recruitmentRequirements.getDateRequired())
                .description(recruitmentRequirements.getDescription())
                .expectedSalary(recruitmentRequirements.getExpectedSalary())
                .positions(recruitmentRequirements.getPositions())
                .quantity(recruitmentRequirements.getQuantity())
                .status(recruitmentRequirements.getStatus())
                .departmentDTO(departmentMapper.toDepartmentDTO(recruitmentRequirements.getDepartments()))
                .employeeDTO(employeeMapper.toEmployeeDTO(recruitmentRequirements.getEmployees()))
                .build();
    }

    public RecruitmentDTO toRecruitmentDTO(Recruitment recruitment) {
        return RecruitmentDTO.builder()
                .id(recruitment.getId())
                .email(recruitment.getEmail())
                .deadline(recruitment.getDeadline())
                .position(recruitment.getPosition())
                .createAt(recruitment.getCreateAt()) // Sửa chỗ này: ban đầu là recruitmentDTO.getCreateAt() => lỗi logic
                .jobDescription(recruitment.getJobDescription()) // Tương tự
                .recruitmentRequirementsDTO(toRecruitmentRequirementsDTO(recruitment.getRecruitmentRequirements()))
                .build();
    }
}
