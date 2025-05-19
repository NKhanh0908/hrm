package com.project.hrm.services.impl;

import com.project.hrm.dto.assignmentDTO.AssignmentCreateDTO;
import com.project.hrm.dto.assignmentDTO.AssignmentDTO;
import com.project.hrm.dto.assignmentDTO.AssignmentFilter;
import com.project.hrm.dto.assignmentDTO.AssignmentUpdateDTO;
import com.project.hrm.entities.Assignment;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
import com.project.hrm.mapper.AssignmentMapper;
import com.project.hrm.repositories.AssignmentRepository;
import com.project.hrm.services.AssignmentService;
import com.project.hrm.services.DepartmentService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;

    private final AssignmentMapper assignmentMapper;

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final RoleService roleService;



    @Transactional
    @Override
    public AssignmentDTO create(AssignmentCreateDTO assignmentCreateDTO) {
        Departments departments = departmentService.getEntityById(assignmentCreateDTO.getDepartmentId());

        Employees employees = employeeService.getEntityById(assignmentCreateDTO.getEmployeeId());

        Role role = roleService.getEntityById(assignmentCreateDTO.getRoleId());

        Assignment assignment = new Assignment();

        assignment.setDepartments(departments);
        assignment.setEmployees(employees);
        assignment.setRole(role);


        return assignmentMapper.toDTO(assignmentRepository.save(assignment), employees, departments, role);
    }

    @Override
    public AssignmentDTO update(AssignmentUpdateDTO assignmentUpdateDTO) {
        return null;
    }

    @Override
    public List<AssignmentDTO> filter(AssignmentFilter assignmentFilter, int page, int size) {
        return List.of();
    }

    @Override
    public Assignment getEntityById(Integer id) {
        return null;
    }
}
