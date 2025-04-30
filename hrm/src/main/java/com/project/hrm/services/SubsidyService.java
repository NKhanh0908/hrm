package com.project.hrm.services;

import com.project.hrm.dto.employeeDTO.SubsidyCreateDTO;
import com.project.hrm.dto.employeeDTO.SubsidyDTO;
import com.project.hrm.dto.employeeDTO.SubsidyUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubsidyService {
    List<SubsidyDTO> getAll();

    SubsidyDTO getById(Integer id);

    Boolean checkExists(Integer subsidyId);

    SubsidyDTO create(SubsidyCreateDTO subsidyCreateDTO);

    SubsidyDTO update(SubsidyUpdateDTO subsidyUpdateDTO);

    void delete(Integer subsidyId);
}