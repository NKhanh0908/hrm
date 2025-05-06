package com.project.hrm.services;

import com.project.hrm.dto.salaryDTO.SalaryCreateDTO;
import com.project.hrm.dto.salaryDTO.SalaryDTO;
import com.project.hrm.dto.salaryDTO.SalaryUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalaryService {
    List<SalaryDTO> getAll();

    SalaryDTO getById(Integer id);

    Boolean checkExists(Integer salaryId);

    SalaryDTO create(SalaryCreateDTO salaryCreateDTO);

    SalaryDTO update(SalaryUpdateDTO salaryUpdateDTO);

    void delete(Integer salaryId);
}