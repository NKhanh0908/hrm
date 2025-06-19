package com.project.hrm.dto.employeeDTO;

import lombok.Data;

@Data
public class EmployeeFilter {
    private String name;
    private String email;
    private String gender;
    private String address;
    private String status;
}
