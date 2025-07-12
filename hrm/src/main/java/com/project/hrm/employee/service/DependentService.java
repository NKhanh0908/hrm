package com.project.hrm.employee.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.dependentDTO.DependentCreateDTO;
import com.project.hrm.employee.dto.dependentDTO.DependentDTO;
import com.project.hrm.employee.dto.dependentDTO.DependentUpdateDTO;
import com.project.hrm.employee.entity.Dependent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DependentService {
    DependentDTO create(DependentCreateDTO dependentCreateDTO);

    DependentDTO update(DependentUpdateDTO dependentUpdateDTO);

    void delete(Integer id);

    Boolean checkExists(Integer id);

    DependentDTO getById(Integer id);

    Dependent getEntityById(Integer id);

    List<DependentDTO> getDependentsByEmployeeId(Integer employeeId);

    PageDTO<DependentDTO> getAllDependents(int page, int size);

    int countDependentsOfEmployee(Integer employeeId);

    Map<Integer, Integer> getDependentCountsForEmployees(List<Integer> employeeIds);
}
