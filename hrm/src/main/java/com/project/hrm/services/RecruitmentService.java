package com.project.hrm.services;

import com.project.hrm.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentService {
    List<RecruitmentDTO> getAll();

    RecruitmentDTO getById(Integer id);

    Boolean checkExists(Integer recruitmentId);

    RecruitmentDTO create(RecruitmentCreateDTO recruitmentCreateDTO);

    RecruitmentDTO update(RecruitmentUpdateDTO recruitmentUpdateDTO);

    void delete(Integer recruitmentId);
}