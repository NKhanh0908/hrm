package com.project.hrm.dto.employeeDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeFilter {
    private String name;
    private String email;
    private String gender;
    private LocalDate address;
    private String status;
}
