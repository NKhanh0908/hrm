package com.project.hrm.mapper;

import com.project.hrm.dto.applyDTO.ApplyDTO;
import com.project.hrm.dto.candidateProfileDTO.CandidateProfileCreateDTO;
import com.project.hrm.dto.candidateProfileDTO.CandidateProfileDTO;
import com.project.hrm.dto.candidateProfileDTO.CandidateProfileUpdateDTO;
import com.project.hrm.dto.evaluateDTO.EvaluateDTO;
import com.project.hrm.entities.Apply;
import com.project.hrm.entities.CandidateProfile;
import com.project.hrm.entities.Evaluate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    public CandidateProfile convertCreateToEntity(CandidateProfileCreateDTO candidateProfileCreateDTO){
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

    public CandidateProfile convertUpdateToEntity(CandidateProfileUpdateDTO candidateProfileUpdateDTO){
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
    public EvaluateDTO toEvaluateDTO(Evaluate evaluate) {
        return EvaluateDTO.builder()
                .id(evaluate.getId())
                .evaluate(evaluate.getEvaluate())
                .feedback(evaluate.getFeedback())
                .feedbackAt(evaluate.getFeedbackAt())
                .build();
    }

    public ApplyDTO toApplyDTO(Apply apply) {
        return ApplyDTO.builder()
                .id(apply.getId())
                .applyAt(apply.getApplyAt())
                .status(apply.getStatus())
                .position(apply.getPosition())
                .candidateProfileDTO(toCandidateProfileDTO(apply.getCandidateProfile()))
                .build();
    }
}
