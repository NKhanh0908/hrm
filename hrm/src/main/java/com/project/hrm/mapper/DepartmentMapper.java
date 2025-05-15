package com.project.hrm.mapper;

import com.project.hrm.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.dto.departmentDTO.DepartmentUpdateDTO;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Employees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public Departments convertCreateToEntity(DepartmentCreateDTO departmentCreateDTO){
        return Departments.builder()
                .address(departmentCreateDTO.getAddress())
                .phone(departmentCreateDTO.getPhone())
                .email(departmentCreateDTO.getEmail())
                .departmentName(departmentCreateDTO.getDepartmentName())
                .description(departmentCreateDTO.getDescription())
                .description(departmentCreateDTO.getDescription())
                .build();
    }

    public Departments convertUpdateToEntity(DepartmentUpdateDTO departmentUpdateDTO){
        return Departments.builder()
                .address(departmentUpdateDTO.getAddress())
                .phone(departmentUpdateDTO.getPhone())
                .email(departmentUpdateDTO.getEmail())
                .departmentName(departmentUpdateDTO.getDepartmentName())
                .description(departmentUpdateDTO.getDescription())
                .description(departmentUpdateDTO.getDescription())
                .build();
    }

    public Page<DepartmentDTO> convertPageEntityToPageDTO(Page<Departments> departmentsPage){
        List<DepartmentDTO> departmentDTOList = departmentsPage
                .stream()
                .map(this::toDepartmentDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(departmentDTOList, departmentsPage.getPageable(), departmentsPage.getTotalElements());
    }

}
