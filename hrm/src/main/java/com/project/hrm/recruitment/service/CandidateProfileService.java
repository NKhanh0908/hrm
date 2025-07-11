package com.project.hrm.recruitment.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileCreateDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileFilter;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileUpdateDTO;
import com.project.hrm.recruitment.entity.CandidateProfile;

public interface CandidateProfileService {
    CandidateProfileDTO create(CandidateProfileCreateDTO dto);

    CandidateProfileDTO update(CandidateProfileUpdateDTO dto);

    void delete(Integer id);

    CandidateProfileDTO getById(Integer id);

    CandidateProfile getEntityById(Integer id);

    CandidateProfileDTO checkExistsCandidateProfile(String email);

    CandidateProfile getEntityByApplyId(Integer applyId);

    PageDTO<CandidateProfileDTO> filter(CandidateProfileFilter candidateProfileFilter, int page, int size);

}
