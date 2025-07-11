package com.project.hrm.recruitment.mapper;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.mapper.DepartmentMapper;
import com.project.hrm.employee.mapper.EmployeeMapper;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementsDTO;
import com.project.hrm.recruitment.entity.RecruitmentRequirements;
import com.project.hrm.recruitment.enums.RecruitmentRequirementsStatus;
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
                .employeeId(recruitmentRequirements.getRequirements().getId())
                .employeeName(recruitmentRequirements.getRequirements().fullName())
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

    public PageDTO<RecruitmentRequirementsDTO> toRecruitmentRequirementsPageDTO(Page<RecruitmentRequirements> page) {
        return PageDTO.<RecruitmentRequirementsDTO>builder()
                .content(page.getContent().stream()
                        .map(this::toDTO)
                        .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
