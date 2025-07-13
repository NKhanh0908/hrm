package com.project.hrm.department.dto.departmentDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentFilter {
    @Schema(description = "Filter by department name (contains)", example = "HR", nullable = true)
    private String departmentName;

    @Schema(description = "Filter by department address", example = "Hanoi", nullable = true)
    private String address;

    @Schema(description = "Filter by department email", example = "hr@company.com", nullable = true)
    private String email;
}
