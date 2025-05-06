package com.project.hrm.services.impl;

import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.mapper.DepartmentMapper;
import com.project.hrm.repositories.DepartmentRepository;
import com.project.hrm.services.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentDTO getById(Integer id) {
        return departmentMapper.toDepartmentDTO(departmentRepository.findById(id)
                .orElseThrow());
    }
}
