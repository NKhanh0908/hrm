package com.project.hrm.services;

import com.project.hrm.dto.evaluateDTO.EvaluateCreateDTO;
import com.project.hrm.dto.evaluateDTO.EvaluateDTO;
import com.project.hrm.dto.evaluateDTO.EvaluateUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EvaluateService {
    List<EvaluateDTO> getAll();

    EvaluateDTO getById(Integer id);

    Boolean checkExists(Integer evaluateId);

    EvaluateDTO create(EvaluateCreateDTO evaluateCreateDTO);

    EvaluateDTO update(EvaluateUpdateDTO evaluateUpdateDTO);

    void delete(Integer evaluateId);
}