package com.project.hrm.dto.attendenceDTO;

import com.project.hrm.enums.AttendenceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendenceUpdateDTO {
    private Integer id;
    private Integer employeeId;
    private LocalDateTime attendenceDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Float regularTime;
    private Float otherTime;
    private AttendenceType shiftType;
}
