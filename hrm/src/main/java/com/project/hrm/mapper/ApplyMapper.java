package com.project.hrm.mapper;

import com.project.hrm.dto.applyDTO.ApplyCreateDTO;
import com.project.hrm.dto.applyDTO.ApplyDTO;
import com.project.hrm.entities.Apply;
import com.project.hrm.entities.CandidateProfile;
import com.project.hrm.entities.Recruitment;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
                .status(apply.getStatus())
                .applyAt(apply.getApplyAt())
                .position(apply.getPosition())
                .recruitmentDTO(recruitmentMapper.toDTO(apply.getRecruitment()))
                .candidateProfileDTO(candidateProfileMapper.toCandidateProfileDTO(apply.getCandidateProfile()))
                .build();
    }

    public Apply convertCreateDTOToEntity(ApplyCreateDTO applyCreateDTO, Recruitment recruitment, CandidateProfile candidateProfile){
        return Apply.builder()
                .status(applyCreateDTO.getStatus())
                .position(applyCreateDTO.getPosition())
                .recruitment(recruitment)
                .candidateProfile(candidateProfile)
                .build();
    }

    public List<ApplyDTO> convertPageToList(Page<Apply> applyPage){
        return applyPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
