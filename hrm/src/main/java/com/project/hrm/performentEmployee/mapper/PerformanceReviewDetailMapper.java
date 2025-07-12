package com.project.hrm.performentEmployee.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO.PerformanceReviewDetailCreateDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO.PerformanceReviewDetailDTO;
import com.project.hrm.performentEmployee.entity.PerformanceReviewDetail;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PerformanceReviewDetailMapper {
    public PerformanceReviewDetail convertCreateDTOToEntity(PerformanceReviewDetailCreateDTO performanceReviewDetailCreateDTO){
        return PerformanceReviewDetail.builder()
                .criteriaName(performanceReviewDetailCreateDTO.getCriteriaName())
                .criteriaDescription(performanceReviewDetailCreateDTO.getCriteriaDescription())
                .comment(performanceReviewDetailCreateDTO.getComment())
                .score(performanceReviewDetailCreateDTO.getScore())
                .reviewDate(LocalDateTime.now())
                .build();
    }

    public PerformanceReviewDetailDTO convertEntityToDTO(PerformanceReviewDetail performanceReviewDetail){
        return PerformanceReviewDetailDTO.builder()
                .id(performanceReviewDetail.getId())
                .criteriaName(performanceReviewDetail.getCriteriaName())
                .criteriaDescription(performanceReviewDetail.getCriteriaDescription())
                .comment(performanceReviewDetail.getComment())
                .score(performanceReviewDetail.getScore())
                .reviewDate(performanceReviewDetail.getReviewDate())
                .reviewerName(performanceReviewDetail.getReviewer().fullName())
                .performanceReviewId(performanceReviewDetail.getPerformanceReview().getId())
                .build();
    }

    public List<PerformanceReviewDetailDTO> convertEntityListToDTOList(List<PerformanceReviewDetail> performanceReviewDetailList){
        return performanceReviewDetailList.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<PerformanceReviewDetailDTO> toReviewDetailPageDTO(Page<PerformanceReviewDetail> page) {
        return PageDTO.<PerformanceReviewDetailDTO>builder()
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
