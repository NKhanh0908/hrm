package com.project.hrm.recruitment.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.recruitment.dto.evaluateDTO.EvaluateCreateDTO;
import com.project.hrm.recruitment.dto.evaluateDTO.EvaluateDTO;
import com.project.hrm.recruitment.dto.evaluateDTO.EvaluateFilter;
import com.project.hrm.recruitment.dto.evaluateDTO.EvaluateUpdateDTO;
import org.springframework.stereotype.Service;

@Service
public interface EvaluateService {
    PageDTO<EvaluateDTO> filter(EvaluateFilter evaluateFilter , int page, int size);

    EvaluateDTO getById(Integer id);

    EvaluateDTO create(EvaluateCreateDTO evaluateCreateDTO);

    EvaluateDTO update(EvaluateUpdateDTO evaluateUpdateDTO);

    void delete(Integer evaluateId);
}