package com.project.hrm.services;

import com.project.hrm.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.dto.departmentDTO.DepartmentFilter;
import com.project.hrm.dto.departmentDTO.DepartmentUpdateDTO;
import com.project.hrm.entities.Departments;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {
    DepartmentDTO create(DepartmentCreateDTO departmentCreateDTO);

    DepartmentDTO update(DepartmentUpdateDTO departmentUpdateDTO);

    DepartmentDTO getById(Integer id);

    Departments getEntityById(Integer id);

    List<DepartmentDTO> filter(DepartmentFilter departmentFilter, int page, int size);

    DepartmentDTO appointmentOfDean(Integer departmentId, Integer employeeId);


}
