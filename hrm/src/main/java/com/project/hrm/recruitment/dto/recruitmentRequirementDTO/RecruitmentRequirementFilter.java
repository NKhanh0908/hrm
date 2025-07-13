package com.project.hrm.recruitment.dto.recruitmentRequirementDTO;

import com.project.hrm.recruitment.enums.RecruitmentRequirementsStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecruitmentRequirementFilter {
    @Schema(description = "Position title or keyword", example = "Software Engineer", nullable = true)
    private String positions;

    @Schema(
            description = "Status of the recruitment requirement",
            example = "OPEN",
            nullable = true,
            implementation = RecruitmentRequirementsStatus.class
    )
    private RecruitmentRequirementsStatus status;

    @Schema(description = "Filter by creation date from", example = "2025-07-01T00:00:00", nullable = true)
    private LocalDateTime dateFrom;

    @Schema(description = "Filter by creation date to", example = "2025-07-31T23:59:59", nullable = true)
    private LocalDateTime dateTo;

    @Schema(description = "ID of the department", example = "3", nullable = true)
    private Integer departmentId;

    @Schema(description = "ID of the role", example = "2", nullable = true)
    private Integer roleId;
}
