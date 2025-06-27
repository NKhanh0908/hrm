package com.project.hrm.dto.dayOffDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayOffResponseForPayrollDTO {
    private int summaryDayOff;
    private int summaryDayOffNoAccept;
}
