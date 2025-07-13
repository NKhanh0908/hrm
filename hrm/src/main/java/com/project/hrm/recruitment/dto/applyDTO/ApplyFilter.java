package com.project.hrm.recruitment.dto.applyDTO;

import com.project.hrm.recruitment.enums.ApplyStatus;
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
public class ApplyFilter {
    @Schema(description = "Application datetime", example = "2024-06-01T08:30:00", nullable = true)
    private LocalDateTime applyAt;

    @Schema(
            description = "Application status",
            example = "PENDING",
            nullable = true,
            implementation = ApplyStatus.class
    )
    private ApplyStatus status;

    @Schema(description = "Position applied for", example = "Software Engineer", nullable = true)
    private String position;

    @Schema(description = "Recruitment ID", example = "10", nullable = true)
    private Integer recruitmentID;
}
