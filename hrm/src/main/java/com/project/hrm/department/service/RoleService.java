package com.project.hrm.department.service;

import com.project.hrm.department.dto.roleDTO.RoleCreateDTO;
import com.project.hrm.department.dto.roleDTO.RoleDTO;
import com.project.hrm.department.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    Role getEntityById(Integer id);

    RoleDTO getDTOById(Integer id);

    RoleDTO create(RoleCreateDTO roleCreateDTO);

    List<RoleDTO> getAllByDepartmentId(Integer departmentId);
}
