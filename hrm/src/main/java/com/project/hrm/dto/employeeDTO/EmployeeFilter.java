package com.project.hrm.dto.employeeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFilter {
    private String name;
    private String email;
    private String gender;
    private String address;
}
