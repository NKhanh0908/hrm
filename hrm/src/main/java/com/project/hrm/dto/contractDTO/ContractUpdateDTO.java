package com.project.hrm.dto.contractDTO;

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
public class ContractUpdateDTO {
    @NotNull(message = "Contract id is required")
    private Integer id;

    private String title;
    private LocalDateTime contractSigningDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double baseSalary;
    private String description;
    private Integer employeeId;
    private Integer departmentId;
    private Integer roleId;
}
