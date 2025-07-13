package com.project.hrm.recruitment.dto.recruitmentDTO;

import com.project.hrm.recruitment.enums.RecruitmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentFilter {
    @Schema(description = "Filter by deadline from (inclusive)", example = "2025-07-01T00:00:00", nullable = true)
    private LocalDateTime deadlineFrom;

    @Schema(description = "Filter by deadline to (inclusive)", example = "2025-07-31T23:59:59", nullable = true)
    private LocalDateTime deadlineTo;

    @Schema(description = "Filter by creation date from", example = "2025-06-01T00:00:00", nullable = true)
    private LocalDateTime dateCreateFrom;

    @Schema(description = "Filter by creation date to", example = "2025-06-30T23:59:59", nullable = true)
    private LocalDateTime dateCreateTo;

    @Schema(description = "ID of related recruitment requirement", example = "7", nullable = true)
    private Integer recruitmentRequirementID;

    @Schema(
            description = "Status of the recruitment post",
            example = "OPEN",
            nullable = true,
            implementation = RecruitmentStatus.class
    )
    private RecruitmentStatus status;
}
