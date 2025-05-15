package com.project.hrm.services;

import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsUpdateDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface RecruitmentRequirementService {
    List<RecruitmentRequirementsDTO> filterRecruitmentRequirements(String positions, String status, LocalDateTime dateFrom, LocalDateTime dateTo, Integer departmentId, int page, int size);

    RecruitmentRequirementsDTO getById(Integer id);

    Boolean checkExists(Integer requirementId);

    RecruitmentRequirementsDTO create(RecruitmentRequirementsCreateDTO requirementCreateDTO);

    RecruitmentRequirementsDTO update(RecruitmentRequirementsUpdateDTO requirementUpdateDTO);

    void delete(Integer requirementId);
}