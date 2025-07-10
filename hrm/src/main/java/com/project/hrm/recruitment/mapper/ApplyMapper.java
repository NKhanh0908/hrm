package com.project.hrm.recruitment.mapper;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.recruitment.dto.applyDTO.ApplyCreateDTO;
import com.project.hrm.recruitment.dto.applyDTO.ApplyDTO;
import com.project.hrm.recruitment.entity.Apply;
import com.project.hrm.recruitment.entity.CandidateProfile;
import com.project.hrm.recruitment.entity.Recruitment;
import com.project.hrm.recruitment.enums.ApplyStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ApplyMapper {
    private final RecruitmentMapper recruitmentMapper;
    private final CandidateProfileMapper candidateProfileMapper;

    public ApplyDTO toDTO(Apply apply){
        return ApplyDTO.builder()
                .id(apply.getId())
                .status(apply.getApplyStatus().toString())
                .applyAt(apply.getApplyAt())
                .position(apply.getPosition())
                .recruitmentDTO(recruitmentMapper.toDTO(apply.getRecruitment()))
                .candidateProfileDTO(candidateProfileMapper.toCandidateProfileDTO(apply.getCandidateProfile()))
                .build();
    }

    public Apply convertCreateDTOToEntity(ApplyCreateDTO applyCreateDTO, Recruitment recruitment, CandidateProfile candidateProfile){
        return Apply.builder()
                .applyAt(LocalDateTime.now())
                .applyStatus(ApplyStatus.SUBMITTED)
                .position(applyCreateDTO.getPosition())
                .recruitment(recruitment)
                .candidateProfile(candidateProfile)
                .build();
    }

    public PageDTO<ApplyDTO> toApplyPageDTO(Page<Apply> applyPage){
        return PageDTO.<ApplyDTO>builder()
                .content(
                        applyPage.getContent()
                                .stream()
                                .map(this::toDTO)
                                .collect(Collectors.toList())
                )
                .page(applyPage.getNumber())
                .size(applyPage.getSize())
                .totalElements(applyPage.getTotalElements())
                .totalPages(applyPage.getTotalPages())
                .build();
    }

    public List<ApplyDTO> convertPageToList(Page<Apply> applyPage){
        return applyPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
