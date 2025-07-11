package com.project.hrm.department.mapper;

import com.project.hrm.department.dto.roleDTO.RoleDTO;
import com.project.hrm.department.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {
    public RoleDTO convertEntityToDTO(Role role){
        return RoleDTO.builder()
                .id(role.getId())
                .roleName(role.getName())
                .departmentId(role.getDepartments().getId())
                .departmentName(role.getDepartments().getDepartmentName())
                .build();
    }

    public List<RoleDTO> convertListEntityToListDTO(List<Role> roles){
        return roles.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }
}
