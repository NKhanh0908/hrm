package com.project.hrm.payroll.dto.regulationsDTO;

import com.project.hrm.payroll.enums.PayrollComponentType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegulationsCreateDTO {
    @Pattern(regexp = "[A-Za-z0-9_-]+", message = "Regulation key can only contain letters, numbers, underscores, and hyphens")
    @Size(max = 50, message = "Regulation key cannot exceed 50 characters")
    private String regulationKey;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotNull(message = "Regulation type can't null")
    private PayrollComponentType type;

    @DecimalMin(value = "0.0", inclusive = true, message = "Amount cannot be negative")
    private BigDecimal amount;

    @DecimalMin(value = "0.0", message = "Percentage cannot be negative")
    @DecimalMax(value = "100.0", message = "Percentage cannot exceed 100")
    private Float percentage;

    @DecimalMin(value = "0.0", inclusive = true, message = "Applicable salary cannot be negative")
    private BigDecimal applicableSalary;

    @NotNull(message = "Effective date cannot be null")
    @PastOrPresent(message = "Effective date must be today or in the past")
    private LocalDateTime effectiveDate;
}
