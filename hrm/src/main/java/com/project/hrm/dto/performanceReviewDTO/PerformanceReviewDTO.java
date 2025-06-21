package com.project.hrm.dto.performanceReviewDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceReviewDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime reviewStartDate;
    private LocalDateTime reviewEndDate;
    private String status;
    private String reviewCycle;
    private String overallComment;
    private Float overallScore;
    private Integer employeeId;
    private String employeeName;
    private Integer employeeRequestId;
    private String employeeRequestName;
    private Integer approverId;
    private String approverName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
