package com.project.hrm.performentEmployee.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDTO.PerformanceReviewCreateDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDTO.PerformanceReviewDTO;
import com.project.hrm.performentEmployee.entity.PerformanceReview;
import com.project.hrm.performentEmployee.enums.PerformanceReviewStatus;
import com.project.hrm.performentEmployee.enums.ReviewCycle;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PerformanceReviewMapper {
    public PerformanceReview convertCreateDTOToEntity(PerformanceReviewCreateDTO performanceReviewCreateDTO){
        return PerformanceReview.builder()
                .title(performanceReviewCreateDTO.getTitle())
                .description(performanceReviewCreateDTO.getDescription())
                .reviewStartDate(performanceReviewCreateDTO.getReviewStartDate())
                .reviewEndDate(performanceReviewCreateDTO.getReviewEndDate())
                .status(PerformanceReviewStatus.SCHEDULED)
                .reviewCycle(ReviewCycle.valueOf(performanceReviewCreateDTO.getReviewCycle()))
                .createdAt(LocalDateTime.now())
                .build();
    }

    public PerformanceReviewDTO convertEntityToDTO(PerformanceReview performanceReview){
        return PerformanceReviewDTO.builder()
                .id(performanceReview.getId())
                .title(performanceReview.getTitle())
                .description(performanceReview.getDescription())
                .reviewStartDate(performanceReview.getReviewStartDate())
                .reviewEndDate(performanceReview.getReviewEndDate())
                .status(performanceReview.getStatus().name())
                .reviewCycle(performanceReview.getReviewCycle().name())
                .overallComment(performanceReview.getOverallComment() == null ? "" : performanceReview.getOverallComment())
                .overallScore(performanceReview.getOverallScore() == null ? 0 : performanceReview.getOverallScore())
                .employeeId(performanceReview.getEmployee().getId())
                .employeeName(performanceReview.getEmployee().fullName())
                .employeeRequestId(performanceReview.getEmployeeRequest().getId())
                .employeeRequestName(performanceReview.getEmployeeRequest().fullName())
                .approverId(performanceReview.getApprover() == null ? null : performanceReview.getApprover().getId())
                .approverName(performanceReview.getApprover() == null ? null : performanceReview.getApprover().fullName())
                .createdAt(performanceReview.getCreatedAt())
                .updatedAt(performanceReview.getUpdatedAt() == null ? null : performanceReview.getUpdatedAt())
                .build();
    }

    public List<PerformanceReviewDTO> convertListEntityToListDTO(List<PerformanceReview> performanceReviews){
        return performanceReviews.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<PerformanceReviewDTO> toPerformanceReviewPageDTO(Page<PerformanceReview> page) {
        return PageDTO.<PerformanceReviewDTO>builder()
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
