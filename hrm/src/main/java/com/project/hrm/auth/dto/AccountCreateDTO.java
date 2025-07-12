package com.project.hrm.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateDTO {
    private String username;
    private String password;

    @NotNull(message = "Employee id account is required")
    private Integer employeeId;

    @NotNull(message = "Role id account is required")
    private String role;
}
