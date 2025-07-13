package com.project.hrm.employee.dto.attendanceDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceFilterWithRange {
    @Schema(description = "Employee ID", example = "5", nullable = true)
    private Integer employeeId;

    @Schema(description = "Start of attendance date range", example = "2025-07-01T00:00:00", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime attendanceDateFrom;

    @Schema(description = "End of attendance date range", example = "2025-07-31T23:59:59", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime attendanceDateTo;

    @Schema(description = "Start of check-in time range", example = "2025-07-13T08:00:00", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime checkInFrom;

    @Schema(description = "End of check-in time range", example = "2025-07-13T09:00:00", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime checkInTo;

    @Schema(description = "Start of check-out time range", example = "2025-07-13T16:00:00", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime checkOutFrom;

    @Schema(description = "End of check-out time range", example = "2025-07-13T18:00:00", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime checkOutTo;

    @Schema(description = "Minimum regular working hours", example = "7.5", nullable = true)
    private Float regularTimeFrom;

    @Schema(description = "Maximum regular working hours", example = "9.0", nullable = true)
    private Float regularTimeTo;

    @Schema(description = "Minimum other working hours", example = "0.0", nullable = true)
    private Float otherTimeFrom;

    @Schema(description = "Maximum other working hours", example = "2.0", nullable = true)
    private Float otherTimeTo;
}
