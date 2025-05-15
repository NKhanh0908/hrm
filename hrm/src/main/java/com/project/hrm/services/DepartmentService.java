package com.project.hrm.services;

import com.project.hrm.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.dto.departmentDTO.DepartmentUpdateDTO;
import com.project.hrm.entities.Departments;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {
    Departments findById(Integer id);
    DepartmentDTO getById(Integer id);
    DepartmentDTO create(DepartmentCreateDTO departmentCreateDTO);
    DepartmentDTO update(DepartmentUpdateDTO departmentUpdateDTO);
    Page<DepartmentDTO> filterDepartment(String departmentName, String address, String email, int page, int size);
    DepartmentDTO appointmentOfDean(Integer departmentId, Integer employeeId);
}
