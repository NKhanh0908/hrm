package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.candidateProfileDTO.*;
import com.project.hrm.entities.CandidateProfile;

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
