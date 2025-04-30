package com.project.hrm.mapper;

import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.entities.Departments;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentDTO toDepartmentDTO(Departments departments){
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(departments.getId());
        departmentDTO.setAddress(departments.getAddress());
        departmentDTO.setPhone(departments.getPhone());
        departmentDTO.setDepartmentName(departments.getDepartmentName());
        departmentDTO.setEmail(departments.getEmail());
        departmentDTO.setDescription(departments.getDescription());
        return departmentDTO;
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
