package com.project.hrm.employee.dto.employeeDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeFilter {
    private String name;
    private String email;
    private String gender;
    private String address;
    private String status;
    private Integer roleId;
    private Integer departmentId;
}
