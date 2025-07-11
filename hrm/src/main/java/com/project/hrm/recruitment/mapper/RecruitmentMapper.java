package com.project.hrm.recruitment.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.recruitment.entity.Recruitment;
import com.project.hrm.recruitment.entity.RecruitmentRequirements;
import com.project.hrm.recruitment.enums.RecruitmentStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RecruitmentMapper {
    private final RecruitmentRequirementsMapper recruitmentRequirementsMapper;

    public RecruitmentDTO toDTO(Recruitment recruitment) {
        return RecruitmentDTO.builder()
                .id(recruitment.getId())
                .email(recruitment.getEmail())
                .contactPhone(recruitment.getContactPhone())
                .deadline(recruitment.getDeadline())
                .createAt(recruitment.getCreateAt())
                .status(recruitment.getStatus().name())
                .jobDescription(recruitment.getJobDescription())
                .recruitmentRequirementsDTO(
                        recruitmentRequirementsMapper.toDTO(recruitment.getRecruitmentRequirements()))
                .employeeApproveId(recruitment.getApproveBy().getId())
                .employeeApproveName(recruitment.getApproveBy().fullName())
                .build();
    }

    public List<RecruitmentDTO> convertPageEntityToPageDTO(Page<Recruitment> recruitmentPage) {
        return recruitmentPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Recruitment convertCreateToEntity(RecruitmentCreateDTO recruitmentCreateDTO,
            RecruitmentRequirements recruitmentRequirements, Employees employees) {
        return Recruitment.builder()
                .contactPhone(recruitmentCreateDTO.getContactPhone())
                .email(recruitmentCreateDTO.getEmail())
                .deadline(recruitmentCreateDTO.getDeadline())
                .status(RecruitmentStatus.ARCHIVED)
                .jobDescription(recruitmentCreateDTO.getJobDescription())
                .recruitmentRequirements(recruitmentRequirements)
                .createAt(LocalDateTime.now())
                .approveBy(employees)
                .build();
    }

    public PageDTO<RecruitmentDTO> toRecruitmentPageDTO(Page<Recruitment> page) {
        return PageDTO.<RecruitmentDTO>builder()
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
