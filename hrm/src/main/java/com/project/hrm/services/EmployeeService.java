package com.project.hrm.services;


import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<EmployeeDTO> getAll();
    EmployeeDTO getById(Integer id);
    Boolean checkExists(Integer employeeId);
    EmployeeDTO create(EmployeeCreateDTO employeeCreateDTO);
    EmployeeDTO update(EmployeeUpdateDTO employeeUpdateDTO);
    void delete(Integer employeeId);
}
