package com.project.hrm.performentEmployee.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO.FeedbackEmployeeCreateDTO;
import com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO.FeedbackEmployeeDTO;
import com.project.hrm.performentEmployee.entity.FeedbackEmployee;
import com.project.hrm.performentEmployee.enums.ReviewType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedbackEmployeeMapper {
    public FeedbackEmployee convertCreateDTOToEntity(FeedbackEmployeeCreateDTO feedbackEmployeeCreateDTO){
        return FeedbackEmployee.builder()
                .feedbackContent(feedbackEmployeeCreateDTO.getFeedbackContent())
                .strengths(feedbackEmployeeCreateDTO.getStrengths())
                .areasForImprovement(feedbackEmployeeCreateDTO.getAreasForImprovement())
                .suggestions(feedbackEmployeeCreateDTO.getSuggestions())
                .isAnonymous(feedbackEmployeeCreateDTO.getIsAnonymous())
                .feedbackType(ReviewType.valueOf(feedbackEmployeeCreateDTO.getFeedbackType()))
                .createdAt(LocalDateTime.now())
                .build();
    }

    public FeedbackEmployeeDTO convertEntityToDTO(FeedbackEmployee feedbackEmployee){
        return FeedbackEmployeeDTO.builder()
                .id(feedbackEmployee.getId())
                .feedbackContent(feedbackEmployee.getFeedbackContent())
                .strengths(feedbackEmployee.getStrengths())
                .areasForImprovement(feedbackEmployee.getAreasForImprovement())
                .suggestions(feedbackEmployee.getSuggestions())
                .isAnonymous(feedbackEmployee.getIsAnonymous())
                .feedbackType(feedbackEmployee.getFeedbackType().name())
                .performanceReviewId(feedbackEmployee.getPerformanceReview().getId())
                .feedbackProviderId(feedbackEmployee.getFeedbackProvider().getId())
                .feedbackProviderName(feedbackEmployee.getFeedbackProvider().fullName())
                .createdAt(feedbackEmployee.getCreatedAt())
                .build();
    }

    public List<FeedbackEmployeeDTO> convertEntityListToDTOList(List<FeedbackEmployee> feedbackEmployees){
        return feedbackEmployees.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<FeedbackEmployeeDTO> toFeedbackEmployeePageDTO(Page<FeedbackEmployee> page) {
        return PageDTO.<FeedbackEmployeeDTO>builder()
                .content(page.getContent().stream()
                        .map(this::convertEntityToDTO)
                        .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
