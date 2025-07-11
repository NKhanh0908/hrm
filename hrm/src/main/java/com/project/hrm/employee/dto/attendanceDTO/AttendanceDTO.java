package com.project.hrm.employee.dto.attendanceDTO;

import com.project.hrm.employee.enums.AttendanceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private Integer id;
    private Integer employeeId;
    private LocalDateTime attendanceDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Float regularTime;
    private Float otherTime;
    private AttendanceType shiftType;
}
