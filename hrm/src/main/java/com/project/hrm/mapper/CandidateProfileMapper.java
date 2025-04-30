package com.project.hrm.mapper;


import com.project.hrm.dto.departmentDTO.ApplyDTO;
import com.project.hrm.dto.departmentDTO.CandidateProfileDTO;
import com.project.hrm.dto.departmentDTO.EvaluateDTO;
import com.project.hrm.entities.Apply;
import com.project.hrm.entities.CandidateProfile;
import com.project.hrm.entities.Evaluate;
import org.springframework.stereotype.Component;

@Component
public class CandidateProfileMapper {
    public CandidateProfileDTO toCandidateProfileDTO(CandidateProfile candidateProfile){
        CandidateProfileDTO candidateProfileDTO = new CandidateProfileDTO();
        candidateProfileDTO.setId(candidateProfile.getId());
        candidateProfileDTO.setCreateProfileAt(candidateProfile.getCreateProfileAt());
        candidateProfileDTO.setEmail(candidateProfile.getEmail());
        candidateProfileDTO.setPhone(candidateProfile.getPhone());
        candidateProfileDTO.setExperience(candidateProfile.getExperience());
        candidateProfileDTO.setName(candidateProfile.getName());
        candidateProfileDTO.setLinkCV(candidateProfile.getLinkCV());
        candidateProfileDTO.setSkills(candidateProfile.getSkills());
        return candidateProfileDTO;
    }

    public EvaluateDTO toEvaluateDTO(Evaluate evaluate){
        EvaluateDTO evaluateDTO = new EvaluateDTO();
        evaluateDTO.setId(evaluate.getId());
        evaluateDTO.setEvaluate(evaluate.getEvaluate());
        evaluateDTO.setFeedback(evaluate.getFeedback());
        evaluateDTO.setSuggest(evaluateDTO.getSuggest());
        evaluateDTO.setFeedbackAt(evaluate.getFeedbackAt());
        return evaluateDTO;
    }

    public ApplyDTO toApplyDTO(Apply apply){
        ApplyDTO applyDTO = new ApplyDTO();
        applyDTO.setId(apply.getId());
        applyDTO.setApplyAt(apply.getApplyAt());
        applyDTO.setStatus(apply.getStatus());
        applyDTO.setPosition(apply.getPosition());
        applyDTO.setCandidateProfileDTO(toCandidateProfileDTO(apply.getCandidateProfile()));
        return applyDTO;
    }
}
