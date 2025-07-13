package com.project.hrm.employee.dto.attendanceDTO;

import com.project.hrm.employee.enums.AttendanceType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCreateDTO {
    @NotNull(message = "Employee ID cannot be null")
    @Positive(message = "Employee ID must be a positive number")
    private Integer employeeId;

    @NotNull(message = "Attendance date cannot be null")
    @PastOrPresent(message = "Attendance date must be today or in the past")
    private LocalDateTime attendanceDate;

    @NotNull(message = "Check-in time cannot be null")
    @PastOrPresent(message = "Check-in time must be today or in the past")
    private LocalDateTime checkIn;

    @NotNull(message = "Shift type cannot be null")
    private AttendanceType shiftType;
}