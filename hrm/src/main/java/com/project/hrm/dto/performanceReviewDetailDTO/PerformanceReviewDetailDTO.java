package com.project.hrm.dto.performanceReviewDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReviewDetailDTO {
    private Integer id;
    private String criteriaName;
    private String criteriaDescription;
    private String reviewerName;
    private String comment;
    private Float score;
    private LocalDateTime reviewDate;
    private Integer performanceReviewId;
}