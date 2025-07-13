package com.project.hrm.employee.dto.disciplinaryActionDTO;

import com.project.hrm.employee.enums.ViolationSeverity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaryActionUpdateDTO {
    @NotNull(message = "Disciplinary action ID cannot be null")
    @Positive(message = "Disciplinary action ID must be a positive number")
    private Integer id;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be today or in the past")
    private LocalDateTime date;

    @NotNull(message = "Employee ID cannot be null")
    @Positive(message = "Employee ID must be a positive number")
    private Integer employeeId;

    @Positive(message = "Regulation ID must be a positive number")
    private Integer regulationId;

    @DecimalMin(value = "0.0", inclusive = true, message = "Penalty amount cannot be negative")
    private BigDecimal penaltyAmount;

    @NotNull(message = "Resolved status cannot be null")
    private Boolean resolved;

    @NotNull(message = "Severity cannot be null")
    private ViolationSeverity severity;
}