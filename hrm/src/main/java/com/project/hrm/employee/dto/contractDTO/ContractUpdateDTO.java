package com.project.hrm.employee.dto.contractDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ContractUpdateDTO {
    @NotNull(message = "Contract ID is required")
    private Integer id;

    @NotBlank(message = "Contract title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotNull(message = "Contract signing date is required")
    private LocalDateTime contractSigningDate;

    @NotNull(message = "Contract start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "Contract end date is required")
    private LocalDateTime endDate;

    @NotNull(message = "Base salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base salary must be greater than 0")
    private Double baseSalary;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    @NotNull(message = "Role ID is required")
    private Integer roleId;
}
