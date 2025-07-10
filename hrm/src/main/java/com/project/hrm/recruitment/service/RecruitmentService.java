package com.project.hrm.recruitment.service;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentFilter;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentUpdateDTO;
import com.project.hrm.recruitment.entity.Recruitment;
import com.project.hrm.recruitment.enums.RecruitmentStatus;
import org.springframework.stereotype.Service;

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