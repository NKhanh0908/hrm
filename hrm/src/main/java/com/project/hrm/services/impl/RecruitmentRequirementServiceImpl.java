package com.project.hrm.services.impl;

import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsUpdateDTO;
import com.project.hrm.mapper.RecruitmentRequirementsMapper;
import com.project.hrm.repositories.RecruitmentRepository;
import com.project.hrm.services.RecruitmentRequirementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RecruitmentRequirementServiceImpl implements RecruitmentRequirementService {
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentRequirementsMapper recruitmentRequirementsMapper;

    @Override
    public List<RecruitmentRequirementsDTO> filterRecruitmentRequirements(String positions, String status, LocalDateTime dateFrom, LocalDateTime dateTo, Integer departmentId, int page, int size) {
        return List.of();
    }

    @Transactional(readOnly = true)
    @Override
    public RecruitmentRequirementsDTO getById(Integer id) {
        return null;
    }

    @Override
    public Boolean checkExists(Integer requirementId) {
        return null;
    }

    @Override
    public RecruitmentRequirementsDTO create(RecruitmentRequirementsCreateDTO requirementCreateDTO) {
        return null;
    }

    @Override
    public RecruitmentRequirementsDTO update(RecruitmentRequirementsUpdateDTO requirementUpdateDTO) {
        return null;
    }

    @Override
    public void delete(Integer requirementId) {

    }

    private Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
