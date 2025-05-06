package com.project.hrm.services;

import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentRequirementService {
    List<RecruitmentRequirementsDTO> getAll();

    RecruitmentRequirementsDTO getById(Integer id);

    Boolean checkExists(Integer requirementId);

    RecruitmentRequirementsDTO create(RecruitmentRequirementsCreateDTO requirementCreateDTO);

    RecruitmentRequirementsDTO update(RecruitmentRequirementsUpdateDTO requirementUpdateDTO);

    void delete(Integer requirementId);
}