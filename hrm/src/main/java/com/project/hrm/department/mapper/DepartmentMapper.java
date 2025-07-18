package com.project.hrm.department.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.department.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.department.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.department.dto.departmentDTO.DepartmentUpdateDTO;
import com.project.hrm.department.entity.Departments;
import org.springframework.data.domain.Page;
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

    public List<DepartmentDTO> convertPageEntityToPageDTO(Page<Departments> departmentsPage){
        return departmentsPage.getContent()
                .stream()
                .map(this::toDepartmentDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<DepartmentDTO> toDepartmentPageDTO(Page<Departments> page) {
        return PageDTO.<DepartmentDTO>builder()
                .content(page.getContent().stream()
                        .map(this::toDepartmentDTO)
                        .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

}
