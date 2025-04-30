package com.project.hrm.services;

import com.project.hrm.dto.departmentDTO.EvaluateCreateDTO;
import com.project.hrm.dto.departmentDTO.EvaluateDTO;
import com.project.hrm.dto.departmentDTO.EvaluateUpdateDTO;
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