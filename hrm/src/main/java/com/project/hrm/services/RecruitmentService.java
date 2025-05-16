package com.project.hrm.services;

import com.project.hrm.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentFilter;
import com.project.hrm.dto.recruitmentDTO.RecruitmentUpdateDTO;
import com.project.hrm.entities.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentService {
    Page<RecruitmentDTO> filter(RecruitmentFilter recruitmentFilter, int page, int size);

    Recruitment getEntityById(Integer id);

    RecruitmentDTO getDTOById(Integer id);

    Boolean checkExists(Integer recruitmentId);

    RecruitmentDTO create(RecruitmentCreateDTO recruitmentCreateDTO);

    RecruitmentDTO update(RecruitmentUpdateDTO recruitmentUpdateDTO);

    void delete(Integer recruitmentId);
}