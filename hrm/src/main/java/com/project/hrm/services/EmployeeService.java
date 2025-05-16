package com.project.hrm.services;


import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.entities.Employees;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    Page<EmployeeDTO> getAll(String name, String email, String gender, String address, int page, int size);
    Employees getEntityById(Integer id);
    EmployeeDTO getDTOById(Integer id);
    Boolean checkExists(Integer employeeId);
    EmployeeDTO create(EmployeeCreateDTO employeeCreateDTO);
    EmployeeDTO update(EmployeeUpdateDTO employeeUpdateDTO);
    void delete(Integer employeeId);
}
