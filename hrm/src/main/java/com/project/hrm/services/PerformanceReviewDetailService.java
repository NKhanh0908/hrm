package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailCreateDTO;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailDTO;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailFilter;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailUpdateDTO;
import com.project.hrm.entities.PerformanceReviewDetail;

public interface PerformanceReviewDetailService {
    PerformanceReviewDetailDTO create(PerformanceReviewDetailCreateDTO performanceReviewDetailCreateDTO);

    PerformanceReviewDetailDTO update(PerformanceReviewDetailUpdateDTO performanceReviewDetailUpdateDTO);

    PerformanceReviewDetailDTO getDTOById(Integer id);

    PerformanceReviewDetail getEntityById(Integer id);

    PageDTO<PerformanceReviewDetailDTO> filter(PerformanceReviewDetailFilter performanceReviewDetailFilter, int page, int size);
}
