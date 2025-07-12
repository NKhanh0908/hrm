package com.project.hrm.employee.dto.contractDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractCreateDTO {
    private String title;
    private LocalDateTime contractSigningDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double baseSalary;
    private String description;

    @NotNull(message = "Contract id is required")
    private Integer employeeId;

    @NotNull(message = "Contract id is required")
    private Integer roleId;
}
