package com.project.hrm.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDTO {
    @NotNull(message = "Account ID is required")
    private Integer id;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;

    @NotNull(message = "Status is required")
    private Boolean status;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    @NotNull(message = "Role is required")
    @Pattern(regexp = "ADMIN|HR|MANAGER|SUPERVISOR|EMPLOYEE",
            message = "Role must be one of: ADMIN, HR, MANAGER, SUPERVISOR, EMPLOYEE")
    private String role;
}
