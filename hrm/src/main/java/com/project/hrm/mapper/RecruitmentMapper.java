package com.project.hrm.mapper;

import com.project.hrm.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.entities.Recruitment;
import com.project.hrm.entities.RecruitmentRequirements;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

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
                .deadline(recruitment.getDeadline())
                .position(recruitment.getPosition())
                .createAt(recruitment.getCreateAt())
                .jobDescription(recruitment.getJobDescription())
                .recruitmentRequirementsDTO(recruitmentRequirementsMapper.toDTO(recruitment.getRecruitmentRequirements()))
                .build();
    }

    public Page<RecruitmentDTO> convertPageEntityToPageDTO(Page<Recruitment> recruitmentPage){
        List<RecruitmentDTO> recruitmentList = recruitmentPage.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(recruitmentList, recruitmentPage.getPageable(), recruitmentPage.getTotalElements());
    }

    public Recruitment convertCreateToEntity(RecruitmentCreateDTO recruitmentCreateDTO, RecruitmentRequirements recruitmentRequirements){
        return Recruitment.builder()
                .position(recruitmentCreateDTO.getPosition())
                .contactPhone(recruitmentCreateDTO.getContactPhone())
                .email(recruitmentCreateDTO.getEmail())
                .deadline(recruitmentCreateDTO.getDeadline())
                .jobDescription(recruitmentCreateDTO.getJobDescription())
                .recruitmentRequirements(recruitmentRequirements)
                .build();
    }

}
