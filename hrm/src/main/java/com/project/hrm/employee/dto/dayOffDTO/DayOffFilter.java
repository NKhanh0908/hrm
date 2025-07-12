package com.project.hrm.employee.dto.dayOffDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayOffFilter {
    private LocalDateTime requestDay;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reason;
    private String status;
}
