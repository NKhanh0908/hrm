package com.project.hrm.dto.dayOffDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayOffUpdateDTO {
    private Integer id;
    private LocalDateTime requestDay;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reason;
    private String status;
    private Integer employeeId;
}
