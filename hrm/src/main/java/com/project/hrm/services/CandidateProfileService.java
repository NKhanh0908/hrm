package com.project.hrm.services;

import com.project.hrm.dto.candidateProfileDTO.*;
import com.project.hrm.entities.CandidateProfile;

import java.util.List;

public interface CandidateProfileService {
    CandidateProfileDTO create(CandidateProfileCreateDTO dto);

    CandidateProfileDTO update(CandidateProfileUpdateDTO dto);

    void delete(Integer id);

    CandidateProfileDTO getById(Integer id);

    CandidateProfile getEntityById(Integer id);

    CandidateProfile getEntityByApplyId(Integer applyId);

    List<CandidateProfileDTO> filter(CandidateProfileFilter candidateProfileFilter, int page, int size);

}
