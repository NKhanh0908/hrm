package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.performanceReviewDTO.PerformanceReviewCreateDTO;
import com.project.hrm.dto.performanceReviewDTO.PerformanceReviewDTO;
import com.project.hrm.dto.performanceReviewDTO.PerformanceReviewFilter;
import com.project.hrm.dto.performanceReviewDTO.PerformanceReviewUpdateDTO;
import com.project.hrm.entities.PerformanceReview;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PerformanceReviewService {
    PerformanceReviewDTO create(PerformanceReviewCreateDTO performanceReviewCreateDTO);

    PerformanceReviewDTO update(PerformanceReviewUpdateDTO performanceReviewUpdateDTO);

    PerformanceReview getEntityById(Integer id);

    PerformanceReviewDTO getDTOById(Integer id);

    PerformanceReviewDTO updateStatus(Integer id, String status);

    PageDTO<PerformanceReviewDTO> filter(PerformanceReviewFilter performanceReviewFilter, int page, int size);
}
