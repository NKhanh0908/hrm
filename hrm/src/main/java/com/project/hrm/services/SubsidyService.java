package com.project.hrm.services;

import com.project.hrm.dto.salaryDTO.SubsidyCreateDTO;
import com.project.hrm.dto.salaryDTO.SubsidyDTO;
import com.project.hrm.dto.salaryDTO.SubsidyUpdateDTO;
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