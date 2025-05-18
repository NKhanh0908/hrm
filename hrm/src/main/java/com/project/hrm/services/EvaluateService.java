package com.project.hrm.services;

import com.project.hrm.dto.evaluateDTO.EvaluateCreateDTO;
import com.project.hrm.dto.evaluateDTO.EvaluateDTO;
import com.project.hrm.dto.evaluateDTO.EvaluateFilter;
import com.project.hrm.dto.evaluateDTO.EvaluateUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EvaluateService {
    List<EvaluateDTO> filter(EvaluateFilter evaluateFilter , int page, int size);

    EvaluateDTO getById(Integer id);

    EvaluateDTO create(EvaluateCreateDTO evaluateCreateDTO);

    EvaluateDTO update(EvaluateUpdateDTO evaluateUpdateDTO);

    void delete(Integer evaluateId);
}