package com.project.hrm.services;

import com.project.hrm.dto.applyDTO.ApplyCreateDTO;
import com.project.hrm.dto.applyDTO.ApplyDTO;
import com.project.hrm.dto.applyDTO.ApplyFilter;
import com.project.hrm.dto.applyDTO.ApplyUpdateDTO;
import com.project.hrm.entities.Apply;
import com.project.hrm.enums.ApplyStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplyService {
    ApplyDTO create(ApplyCreateDTO applyCreateDTO);

    ApplyDTO update(ApplyUpdateDTO applyUpdateDTO);

    ApplyDTO updateStatus(Integer id, ApplyStatus status);

    ApplyDTO getById(Integer id);

    Apply getEntityById(Integer id);

    List<ApplyDTO> filter(ApplyFilter applyFilter, int page, int size);


}
