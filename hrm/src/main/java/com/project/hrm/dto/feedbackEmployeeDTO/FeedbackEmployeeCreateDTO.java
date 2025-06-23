package com.project.hrm.dto.feedbackEmployeeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEmployeeCreateDTO {
    private String feedbackContent;
    private String strengths;
    private String areasForImprovement;
    private String suggestions;
    private Boolean isAnonymous;
    private String feedbackType;
    private Integer feedbackProviderId;
    private Integer performanceReviewId;
}
