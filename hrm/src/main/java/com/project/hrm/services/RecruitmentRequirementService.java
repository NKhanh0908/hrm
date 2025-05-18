package com.project.hrm.services;

import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementFilter;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsUpdateDTO;
import com.project.hrm.entities.RecruitmentRequirements;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentRequirementService {
    RecruitmentRequirementsDTO create(RecruitmentRequirementsCreateDTO recruitmentRequirementsCreateDTO);

    RecruitmentRequirementsDTO update(RecruitmentRequirementsUpdateDTO recruitmentRequirementsUpdateDTO);

    void delete(Integer requirementId);

    RecruitmentRequirements getEntityById(Integer id);

    RecruitmentRequirementsDTO getDTOById(Integer id);

    List<RecruitmentRequirementsDTO> filterRecruitmentRequirements(RecruitmentRequirementFilter recruitmentRequirementFilter, int page, int size);

}