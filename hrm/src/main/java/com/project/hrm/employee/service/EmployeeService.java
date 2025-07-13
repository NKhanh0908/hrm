package com.project.hrm.employee.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.employee.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.employee.dto.employeeDTO.EmployeeFilter;
import com.project.hrm.employee.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.employee.entity.Employees;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EmployeeService {
    EmployeeDTO create(EmployeeCreateDTO employeeCreateDTO);

    EmployeeDTO update(EmployeeUpdateDTO employeeUpdateDTO);

    void delete(Integer employeeId);

    Employees getEntityById(Integer id);

    EmployeeDTO getDTOById(Integer id);

    EmployeeDTO getCurrentEmployee();

    Boolean checkExists(Integer employeeId);

    Employees getEmployeeIsActive(Integer employeeId);

    PageDTO<EmployeeDTO> filter(EmployeeFilter employeeFilter, int page, int size);

    PageDTO<EmployeeDTO> filterByDepartmentID(Integer departmentId, int page, int size);

    Map<Integer ,Employees> getBatchEmployeeForPayPeriod(List<Integer> employeeIds);

    List<Integer> getAllActiveEmployeeIds();
}
