package com.project.hrm.services;

import java.util.List;

import com.project.hrm.dto.salaryDTO.DeductionCreateDTO;
import com.project.hrm.dto.salaryDTO.DeductionDTO;
import com.project.hrm.dto.salaryDTO.DeductionUpdateDTO;

public interface DeductionService {
    List<DeductionDTO> getAll();

    DeductionDTO getById(Integer id);

    Boolean checkExists(Integer deductionId);

    DeductionDTO create(DeductionCreateDTO deductionCreateDTO);

    DeductionDTO update(DeductionUpdateDTO deductionUpdateDTO);

    void delete(Integer deductionId);
}
