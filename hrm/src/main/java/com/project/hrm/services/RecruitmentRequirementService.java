package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.recruitmentDTO.*;
import com.project.hrm.entities.RecruitmentRequirements;
import com.project.hrm.enums.RecruitmentRequirementsStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentRequirementService {
    RecruitmentRequirementsDTO create(RecruitmentRequirementsCreateDTO recruitmentRequirementsCreateDTO);

    RecruitmentRequirementsDTO update(RecruitmentRequirementsUpdateDTO recruitmentRequirementsUpdateDTO);

    RecruitmentRequirementsDTO updateStatus(Integer id, RecruitmentRequirementsStatus status);

    void delete(Integer requirementId);

    RecruitmentRequirements getEntityById(Integer id);

    RecruitmentRequirementsDTO getDTOById(Integer id);

    PageDTO<RecruitmentRequirementsDTO> filterRecruitmentRequirements(RecruitmentRequirementFilter recruitmentRequirementFilter, int page, int size);

}