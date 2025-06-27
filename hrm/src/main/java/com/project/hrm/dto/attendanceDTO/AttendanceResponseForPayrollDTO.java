package com.project.hrm.dto.attendanceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponseForPayrollDTO {
    private float summaryRegularTime;
    private float summaryOverTime;
}
