package com.project.hrm.recruitment.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileCreateDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileUpdateDTO;
import com.project.hrm.recruitment.entity.CandidateProfile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CandidateProfileMapper {

    public CandidateProfileDTO toCandidateProfileDTO(CandidateProfile candidateProfile) {
        return CandidateProfileDTO.builder()
                .id(candidateProfile.getId())
                .createProfileAt(candidateProfile.getCreateProfileAt())
                .email(candidateProfile.getEmail())
                .phone(candidateProfile.getPhone())
                .experience(candidateProfile.getExperience())
                .name(candidateProfile.getName())
                .linkCV(candidateProfile.getLinkCV())
                .skills(candidateProfile.getSkills())
                .build();
    }

    public CandidateProfile toEntity(CandidateProfileDTO candidateProfileDTO) {
        return CandidateProfile.builder()
                .id(candidateProfileDTO.getId())
                .createProfileAt(candidateProfileDTO.getCreateProfileAt())
                .email(candidateProfileDTO.getEmail())
                .phone(candidateProfileDTO.getPhone())
                .experience(candidateProfileDTO.getExperience())
                .name(candidateProfileDTO.getName())
                .linkCV(candidateProfileDTO.getLinkCV())
                .skills(candidateProfileDTO.getSkills())
                .build();
    }

    public CandidateProfile convertCreateToEntity(CandidateProfileCreateDTO candidateProfileCreateDTO) {
        return CandidateProfile.builder()
                .email(candidateProfileCreateDTO.getEmail())
                .phone(candidateProfileCreateDTO.getPhone())
                .experience(candidateProfileCreateDTO.getExperience())
                .name(candidateProfileCreateDTO.getName())
                .linkCV(candidateProfileCreateDTO.getLinkCV())
                .skills(candidateProfileCreateDTO.getSkills())
                .createProfileAt(LocalDateTime.now())
                .build();
    }

    public CandidateProfile convertUpdateToEntity(CandidateProfileUpdateDTO candidateProfileUpdateDTO) {
        return CandidateProfile.builder()
                .id(candidateProfileUpdateDTO.getId())
                .email(candidateProfileUpdateDTO.getEmail())
                .phone(candidateProfileUpdateDTO.getPhone())
                .experience(candidateProfileUpdateDTO.getExperience())
                .name(candidateProfileUpdateDTO.getName())
                .linkCV(candidateProfileUpdateDTO.getLinkCV())
                .skills(candidateProfileUpdateDTO.getSkills())
                .build();
    }

    public List<CandidateProfileDTO> convertPageToList(Page<CandidateProfile> candidateProfilePage) {
        return candidateProfilePage.getContent().stream()
                .map(this::toCandidateProfileDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<CandidateProfileDTO> toCandidateProfilePageDTO(Page<CandidateProfile> page) {
        return PageDTO.<CandidateProfileDTO>builder()
                .content(page.getContent()
                        .stream()
                        .map(this::toCandidateProfileDTO)
                        .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

}
