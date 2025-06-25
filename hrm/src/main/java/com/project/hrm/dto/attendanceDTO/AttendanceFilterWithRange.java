package com.project.hrm.dto.attendanceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceFilterWithRange {
    private Integer employeeId;

    private LocalDateTime attendanceDateFrom;
    private LocalDateTime attendanceDateTo;

    private LocalDateTime checkInFrom;
    private LocalDateTime checkInTo;

    private LocalDateTime checkOutFrom;
    private LocalDateTime checkOutTo;

    private Float regularTimeFrom;
    private Float regularTimeTo;

    private Float otherTimeFrom;
    private Float otherTimeTo;
}
