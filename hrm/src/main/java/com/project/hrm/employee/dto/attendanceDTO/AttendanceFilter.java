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
public class AttendanceFilter {
    @Schema(description = "Employee ID", example = "5", nullable = true)
    private Integer employeeId;

    @Schema(description = "Exact attendance date and time", example = "2025-07-13T08:00:00", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime attendanceDate;

    @Schema(description = "Check-in time", example = "2025-07-13T08:00:00", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime checkIn;

    @Schema(description = "Check-out time", example = "2025-07-13T17:00:00", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime checkOut;

    @Schema(description = "Regular working hours", example = "8.0", nullable = true)
    private Float regularTime;

    @Schema(description = "Other working hours (overtime, leave, etc.)", example = "1.5", nullable = true)
    private Float otherTime;
}
