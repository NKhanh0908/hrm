package com.project.hrm.recruitment.dto.recruitmentRequirementDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentRequirementsUpdateDTO {
    @NotNull(message = "Requirement ID is required")
    private Integer id;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotBlank(message = "Expected salary is required")
    @Size(max = 100, message = "Expected salary must not exceed 100 characters")
    private String expectedSalary;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "PENDING|APPROVED|REJECTED|FILLED",
            message = "Status must be one of: PENDING, APPROVED, REJECTED, FILLED"
    )
    private String status;

    @NotNull(message = "Role ID is required")
    private Integer roleId;
}
