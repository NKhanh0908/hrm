package com.project.hrm.department.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.department.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.department.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.department.dto.departmentDTO.DepartmentFilter;
import com.project.hrm.department.dto.departmentDTO.DepartmentUpdateDTO;
import com.project.hrm.department.entity.Departments;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {
    DepartmentDTO create(DepartmentCreateDTO departmentCreateDTO);

    DepartmentDTO update(DepartmentUpdateDTO departmentUpdateDTO);

    DepartmentDTO getById(Integer id);

    Departments getEntityById(Integer id);

    DepartmentDTO getDepartmentDTOByEmployeeId(Integer employeeId);

    PageDTO<DepartmentDTO> filter(DepartmentFilter departmentFilter, int page, int size);

}
