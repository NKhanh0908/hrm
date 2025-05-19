package com.project.hrm.mapper;

import com.project.hrm.dto.assignmentDTO.AssignmentDTO;
import com.project.hrm.entities.Assignment;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class AssignmentMapper {
    public AssignmentDTO toDTO(Assignment assignment, Employees employees, Departments departments, Role role){
        return AssignmentDTO.builder()
                .id(assignment.getId())
                .departmentName(departments.getDepartmentName())
                .employeeName(employees.fullName())
                .roleName(role.getName())
                .build();
    }
}
