package com.project.hrm.dto.feedbackEmployeeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEmployeeDTO {
    private Integer id;
    private String feedbackContent;
    private String strengths;
    private String areasForImprovement;
    private String suggestions;
    private String feedbackType;
    private Boolean isAnonymous;
    private Integer feedbackProviderId;
    private String feedbackProviderName;
    private Integer performanceReviewId;
    private LocalDateTime createdAt;
}
