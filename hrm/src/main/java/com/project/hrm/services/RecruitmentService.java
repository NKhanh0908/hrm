package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.recruitmentDTO.*;
import com.project.hrm.entities.Recruitment;
import com.project.hrm.enums.RecruitmentStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentService {
    RecruitmentDTO create(RecruitmentCreateDTO recruitmentCreateDTO);

    RecruitmentDTO approved(RecruitmentCreateDTO recruitmentCreateDTO);

    RecruitmentDTO update(RecruitmentUpdateDTO recruitmentUpdateDTO);

    RecruitmentDTO updateStatus(Integer id, RecruitmentStatus recruitmentStatus);

    void delete(Integer recruitmentId);

    Recruitment getEntityById(Integer id);

    RecruitmentDTO getDTOById(Integer id);

    PageDTO<RecruitmentDTO> filter(RecruitmentFilter recruitmentFilter, int page, int size);

}