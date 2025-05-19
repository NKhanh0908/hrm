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
                .dateRequired(recruitmentRequirements.getDateRequired())
                .description(recruitmentRequirements.getDescription())
                .expectedSalary(recruitmentRequirements.getExpectedSalary())
                .positions(recruitmentRequirements.getPositions())
                .quantity(recruitmentRequirements.getQuantity())
                .status(recruitmentRequirements.getStatus().toString())
                .departmentDTO(departmentMapper.toDepartmentDTO(recruitmentRequirements.getDepartments()))
                .employeeDTO(employeeMapper.toEmployeeDTO(recruitmentRequirements.getEmployees()))
                .build();
    }

    public RecruitmentRequirements convertCreateDTOtoEntity(RecruitmentRequirementsCreateDTO recruitmentRequirementsCreateDTO, Departments departments, Employees employees){
        return RecruitmentRequirements.builder()
                .description(recruitmentRequirementsCreateDTO.getDescription())
                .expectedSalary(recruitmentRequirementsCreateDTO.getExpectedSalary())
                .positions(recruitmentRequirementsCreateDTO.getPositions())
                .quantity(recruitmentRequirementsCreateDTO.getQuantity())
                .status(RecruitmentRequirementsStatus.valueOf(recruitmentRequirementsCreateDTO.getStatus()))
                .departments(departments)
                .employees(employees)
                .build();
    }


    public List<RecruitmentRequirementsDTO> toPageEntityToPageDTO(Page<RecruitmentRequirements> recruitmentRequirementsPage){
        return recruitmentRequirementsPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
