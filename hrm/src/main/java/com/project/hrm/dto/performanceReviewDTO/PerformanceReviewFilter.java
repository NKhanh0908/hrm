package com.project.hrm.dto.performanceReviewDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PerformanceReviewFilter {
    private Integer employeeId;
    private Integer employeeRequestId;
    private String approverId;
    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;
    private String status;
    private String reviewCycle;
}
