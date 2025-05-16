package com.project.hrm.services;

import com.project.hrm.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.dto.departmentDTO.DepartmentFilter;
import com.project.hrm.dto.departmentDTO.DepartmentUpdateDTO;
import com.project.hrm.entities.Departments;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {
    Departments getEntityById(Integer id);

    DepartmentDTO getDTOById(Integer id);

    DepartmentDTO create(DepartmentCreateDTO departmentCreateDTO);

    DepartmentDTO update(DepartmentUpdateDTO departmentUpdateDTO);

    Page<DepartmentDTO> filterDepartment(DepartmentFilter departmentFilter, int page, int size);

    DepartmentDTO appointmentOfDean(Integer departmentId, Integer employeeId);
}
