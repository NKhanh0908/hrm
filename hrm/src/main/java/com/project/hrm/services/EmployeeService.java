package com.project.hrm.services;


import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeFilter;
import com.project.hrm.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.entities.Employees;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    EmployeeDTO create(EmployeeCreateDTO employeeCreateDTO);

    EmployeeDTO update(EmployeeUpdateDTO employeeUpdateDTO);

    void delete(Integer employeeId);

    Employees getEntityById(Integer id);

    EmployeeDTO getDTOById(Integer id);

    Boolean checkExists(Integer employeeId);

    List<EmployeeDTO> filter(EmployeeFilter employeeFilter, int page, int size);

    List<EmployeeDTO> filterByDepartmentID(Integer departmentId, int page, int size);
}
