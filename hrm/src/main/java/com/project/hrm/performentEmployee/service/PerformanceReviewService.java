package com.project.hrm.performentEmployee.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDTO.PerformanceReviewCreateDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDTO.PerformanceReviewDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDTO.PerformanceReviewFilter;
import com.project.hrm.performentEmployee.dto.performanceReviewDTO.PerformanceReviewUpdateDTO;
import com.project.hrm.performentEmployee.entity.PerformanceReview;
import org.springframework.stereotype.Service;

@Service
public interface PerformanceReviewService {
    PerformanceReviewDTO create(PerformanceReviewCreateDTO performanceReviewCreateDTO);

    PerformanceReviewDTO update(PerformanceReviewUpdateDTO performanceReviewUpdateDTO);

    PerformanceReview getEntityById(Integer id);

    PerformanceReviewDTO getDTOById(Integer id);

    PerformanceReviewDTO updateStatus(Integer id, String status);

    PageDTO<PerformanceReviewDTO> filter(PerformanceReviewFilter performanceReviewFilter, int page, int size);
}
