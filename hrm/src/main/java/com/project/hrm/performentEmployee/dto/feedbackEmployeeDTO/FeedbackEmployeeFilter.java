package com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEmployeeFilter {
    private Integer employeeId;
    private Integer feedbackProviderId;
    private Integer performanceReviewId;
    private LocalDateTime fromCreateAt;
    private LocalDateTime toCreateAt;
}
