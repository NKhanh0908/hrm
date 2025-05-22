package com.project.hrm.mapper;

import com.project.hrm.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Recruitment;
import com.project.hrm.entities.RecruitmentRequirements;
import com.project.hrm.enums.RecruitmentStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
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
                .position(recruitment.getPosition())
                .createAt(recruitment.getCreateAt())
                .status(recruitment.getStatus().name())
                .jobDescription(recruitment.getJobDescription())
                .recruitmentRequirementsDTO(recruitmentRequirementsMapper.toDTO(recruitment.getRecruitmentRequirements()))
                .employeeApproveId(recruitment.getEmployees().getId())
                .employeeApproveName(recruitment.getEmployees().fullName())
                .build();
    }

    public List<RecruitmentDTO> convertPageEntityToPageDTO(Page<Recruitment> recruitmentPage){
        return recruitmentPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Recruitment convertCreateToEntity(RecruitmentCreateDTO recruitmentCreateDTO, RecruitmentRequirements recruitmentRequirements, Employees employees){
        return Recruitment.builder()
                .position(recruitmentCreateDTO.getPosition())
                .contactPhone(recruitmentCreateDTO.getContactPhone())
                .email(recruitmentCreateDTO.getEmail())
                .deadline(recruitmentCreateDTO.getDeadline())
                .status(RecruitmentStatus.ARCHIVED)
                .jobDescription(recruitmentCreateDTO.getJobDescription())
                .recruitmentRequirements(recruitmentRequirements)
                .createAt(LocalDateTime.now())
                .employees(employees)
                .build();
    }

}
