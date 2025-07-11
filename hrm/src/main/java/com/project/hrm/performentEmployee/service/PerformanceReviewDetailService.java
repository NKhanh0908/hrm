package com.project.hrm.performentEmployee.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO.PerformanceReviewDetailCreateDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO.PerformanceReviewDetailDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO.PerformanceReviewDetailFilter;
import com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO.PerformanceReviewDetailUpdateDTO;
import com.project.hrm.performentEmployee.entity.PerformanceReviewDetail;

public interface PerformanceReviewDetailService {
    PerformanceReviewDetailDTO create(PerformanceReviewDetailCreateDTO performanceReviewDetailCreateDTO);

    PerformanceReviewDetailDTO update(PerformanceReviewDetailUpdateDTO performanceReviewDetailUpdateDTO);

    PerformanceReviewDetailDTO getDTOById(Integer id);

    PerformanceReviewDetail getEntityById(Integer id);

    PageDTO<PerformanceReviewDetailDTO> filter(PerformanceReviewDetailFilter performanceReviewDetailFilter, int page, int size);
}
