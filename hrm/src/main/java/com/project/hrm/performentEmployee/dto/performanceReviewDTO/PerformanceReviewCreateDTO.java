package com.project.hrm.performentEmployee.dto.performanceReviewDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceReviewCreateDTO {
    private String title;
    private String description;
    private LocalDateTime reviewStartDate;
    private LocalDateTime reviewEndDate;
    private String reviewCycle;
    private Integer employeeId;
    private Integer employeeRequestId;
}
