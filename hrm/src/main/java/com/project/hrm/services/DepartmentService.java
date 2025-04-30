package com.project.hrm.services;

import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {
    DepartmentDTO getById(Integer id);
}
