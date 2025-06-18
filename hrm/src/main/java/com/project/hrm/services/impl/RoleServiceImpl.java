package com.project.hrm.services.impl;

import com.project.hrm.dto.roleDTO.RoleCreateDTO;
import com.project.hrm.dto.roleDTO.RoleDTO;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Role;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.RoleMapper;
import com.project.hrm.repositories.RoleRepository;
import com.project.hrm.services.DepartmentService;
import com.project.hrm.services.RoleService;
import com.project.hrm.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    private final DepartmentService departmentService;

    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    @Override
    public Role getEntityById(Integer id) {
        log.info("Find role entity by id: {}", id);

        return roleRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.ROLE_NOT_FOUND));
    }

    @Override
    public RoleDTO create(RoleCreateDTO roleCreateDTO) {
        Departments departments = departmentService.getEntityById(roleCreateDTO.getDepartmentId());

        Role role = new Role();
        role.setId(IdGenerator.getGenerationId());
        role.setName(roleCreateDTO.getRoleName());
        role.setDepartments(departments);

        return roleMapper.convertEntityToDTO(roleRepository.save(role));
    }

    @Override
    public List<RoleDTO> getAllByDepartmentId(Integer departmentId) {
        List<Role> roles = roleRepository.findAllByDepartments_Id(departmentId);

        return roleMapper.convertListEntityToListDTO(roles);
    }
}
