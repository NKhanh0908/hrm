package com.project.hrm.mapper;

import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsDTO;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.RecruitmentRequirements;
import com.project.hrm.enums.RecruitmentRequirementsStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RecruitmentRequirementsMapper {

    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper employeeMapper;


    public RecruitmentRequirementsDTO toDTO(RecruitmentRequirements recruitmentRequirements) {
        return RecruitmentRequirementsDTO.builder()
                .id(recruitmentRequirements.getId())
                .description(recruitmentRequirements.getDescription())
                .quantity(recruitmentRequirements.getQuantity())
                .expectedSalary(recruitmentRequirements.getExpectedSalary())
                .status(recruitmentRequirements.getStatus().toString())
                .dateRequired(recruitmentRequirements.getDateRequired())
                .roleName(recruitmentRequirements.getRole().getName())
                .departmentId(recruitmentRequirements.getRole().getDepartments().getId())
                .departmentName(recruitmentRequirements.getRole().getDepartments().getDepartmentName())
                .employeeId(recruitmentRequirements.getEmployees().getId())
                .employeeName(recruitmentRequirements.getEmployees().fullName())
                .build();
    }

    public RecruitmentRequirements convertCreateDTOtoEntity(RecruitmentRequirementsCreateDTO recruitmentRequirementsCreateDTO){
        return RecruitmentRequirements.builder()
                .description(recruitmentRequirementsCreateDTO.getDescription())
                .expectedSalary(recruitmentRequirementsCreateDTO.getExpectedSalary())
                .quantity(recruitmentRequirementsCreateDTO.getQuantity())
                .dateRequired(LocalDateTime.now())
                .status(RecruitmentRequirementsStatus.PENDING)
                .build();
    }


    public List<RecruitmentRequirementsDTO> toPageEntityToPageDTO(Page<RecruitmentRequirements> recruitmentRequirementsPage){
        return recruitmentRequirementsPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
