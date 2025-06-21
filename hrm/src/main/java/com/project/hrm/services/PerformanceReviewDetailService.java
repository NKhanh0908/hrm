package com.project.hrm.services;

import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailCreateDTO;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailDTO;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailFilter;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailUpdateDTO;
import com.project.hrm.entities.PerformanceReviewDetail;

import java.util.List;

public interface PerformanceReviewDetailService {
    PerformanceReviewDetailDTO create(PerformanceReviewDetailCreateDTO performanceReviewDetailCreateDTO);

    PerformanceReviewDetailDTO update(PerformanceReviewDetailUpdateDTO performanceReviewDetailUpdateDTO);

    PerformanceReviewDetailDTO getDTOById(Integer id);

    PerformanceReviewDetail getEntityById(Integer id);

    List<PerformanceReviewDetailDTO> filter(PerformanceReviewDetailFilter performanceReviewDetailFilter, int page, int size);
}
