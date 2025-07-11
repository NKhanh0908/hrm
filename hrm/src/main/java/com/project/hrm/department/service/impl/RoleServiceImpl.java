package com.project.hrm.department.service.impl;

import com.project.hrm.department.dto.roleDTO.RoleCreateDTO;
import com.project.hrm.department.dto.roleDTO.RoleDTO;
import com.project.hrm.department.entity.Departments;
import com.project.hrm.department.entity.Role;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.department.mapper.RoleMapper;
import com.project.hrm.department.repository.RoleRepository;
import com.project.hrm.department.service.DepartmentService;
import com.project.hrm.department.service.RoleService;
import com.project.hrm.common.utils.IdGenerator;
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

    /**
     * Retrieves a {@link Role} entity by its ID.
     *
     * @param id the ID of the role
     * @return the {@link Role} entity
     * @throws CustomException if the role is not found
     */
    @Transactional(readOnly = true)
    @Override
    public Role getEntityById(Integer id) {
        log.info("Find role entity by id: {}", id);

        return roleRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.ROLE_NOT_FOUND));
    }

    /**
     * Retrieves a {@link RoleDTO} by role ID.
     *
     * @param id the ID of the role
     * @return a {@link RoleDTO} representing the role
     */
    @Transactional(readOnly = true)
    @Override
    public RoleDTO getDTOById(Integer id) {
        return roleMapper.convertEntityToDTO(getEntityById(id));
    }

    /**
     * Creates a new role based on the provided {@link RoleCreateDTO}.
     *
     * @param roleCreateDTO the DTO containing information to create a role
     * @return a {@link RoleDTO} representing the newly created role
     */
    @Transactional
    @Override
    public RoleDTO create(RoleCreateDTO roleCreateDTO) {
        Departments departments = departmentService.getEntityById(roleCreateDTO.getDepartmentId());

        Role role = new Role();
        role.setId(IdGenerator.getGenerationId());
        role.setName(roleCreateDTO.getRoleName());
        role.setDepartments(departments);

        return roleMapper.convertEntityToDTO(roleRepository.save(role));
    }

    /**
     * Retrieves all roles that belong to a specific department.
     *
     * @param departmentId the ID of the department
     * @return a list of {@link RoleDTO} representing the roles in the department
     */
    @Transactional(readOnly = true)
    @Override
    public List<RoleDTO> getAllByDepartmentId(Integer departmentId) {
        List<Role> roles = roleRepository.findAllByDepartments_Id(departmentId);

        return roleMapper.convertListEntityToListDTO(roles);
    }
}
