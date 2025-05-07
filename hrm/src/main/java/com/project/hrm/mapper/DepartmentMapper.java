package com.project.hrm.mapper;

import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.entities.Departments;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentDTO toDepartmentDTO(Departments departments){
        return DepartmentDTO.builder()
        .id(departments.getId())
        .address(departments.getAddress())
        .phone(departments.getPhone())
        .departmentName(departments.getDepartmentName())
        .email(departments.getEmail())
        .description(departments.getDescription())
        .build();
    }

    public Departments toDepartment(DepartmentDTO departmentDTO){
        return Departments.builder()
                .id(departmentDTO.getId())
                .address(departmentDTO.getAddress())
                .phone(departmentDTO.getPhone())
                .email(departmentDTO.getEmail())
                .departmentName(departmentDTO.getDepartmentName())
                .description(departmentDTO.getDescription())
                .description(departmentDTO.getDescription())
                .build();
    }
}
