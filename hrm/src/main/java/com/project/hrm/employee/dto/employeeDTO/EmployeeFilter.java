package com.project.hrm.employee.dto.employeeDTO;

import com.project.hrm.employee.enums.EmployeeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeFilter {
        @Schema(description = "Employee's full name or part of it", example = "John")
        private String name;

        @Schema(description = "Email contains", example = "john@example.com")
        private String email;

        @Schema(description = "Gender (MALE or FEMALE)", example = "MALE")
        private String gender;

        @Schema(description = "Address keyword", example = "New York")
        private String address;

    @Schema(description = "Employee status")
    private EmployeeStatus status;

    @Schema(description = "Role ID", example = "2")
        private Integer roleId;

        @Schema(description = "Department ID", example = "5")
        private Integer departmentId;

}
