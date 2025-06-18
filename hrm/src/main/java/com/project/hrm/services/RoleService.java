package com.project.hrm.services;

import com.project.hrm.dto.roleDTO.RoleCreateDTO;
import com.project.hrm.dto.roleDTO.RoleDTO;
import com.project.hrm.entities.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    Role getEntityById(Integer id);

    RoleDTO create(RoleCreateDTO roleCreateDTO);

    List<RoleDTO> getAllByDepartmentId(Integer departmentId);
}
