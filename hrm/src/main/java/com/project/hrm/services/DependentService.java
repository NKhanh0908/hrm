package com.project.hrm.services;

import com.project.hrm.dto.dependentDTO.DependentCreateDTO;
import com.project.hrm.dto.dependentDTO.DependentDTO;
import com.project.hrm.dto.dependentDTO.DependentUpdateDTO;
import com.project.hrm.entities.Dependent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DependentService {
    DependentDTO create(DependentCreateDTO dependentCreateDTO);

    DependentDTO update(DependentUpdateDTO dependentUpdateDTO);

    void delete(Integer id);

    Boolean checkExists(Integer id);

    DependentDTO getById(Integer id);

    Dependent getEntityById(Integer id);

    List<DependentDTO> getDependentsByEmployeeId(Integer employeeId);

    List<DependentDTO> getAllDependents();
}
