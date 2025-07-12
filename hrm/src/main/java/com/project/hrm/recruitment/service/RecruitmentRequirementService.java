package com.project.hrm.recruitment.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementFilter;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementsDTO;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementsUpdateDTO;
import com.project.hrm.recruitment.entity.RecruitmentRequirements;
import com.project.hrm.recruitment.enums.RecruitmentRequirementsStatus;
import org.springframework.stereotype.Service;

@Service
public interface RecruitmentRequirementService {
    RecruitmentRequirementsDTO create(RecruitmentRequirementsCreateDTO recruitmentRequirementsCreateDTO);

    RecruitmentRequirementsDTO update(RecruitmentRequirementsUpdateDTO recruitmentRequirementsUpdateDTO);

    RecruitmentRequirementsDTO updateStatus(Integer id, RecruitmentRequirementsStatus status);

    void delete(Integer requirementId);

    RecruitmentRequirements getEntityById(Integer id);

    RecruitmentRequirementsDTO getDTOById(Integer id);

    void generateRecruitment(Integer id);

    PageDTO<RecruitmentRequirementsDTO> filterRecruitmentRequirements(RecruitmentRequirementFilter recruitmentRequirementFilter, int page, int size);

}