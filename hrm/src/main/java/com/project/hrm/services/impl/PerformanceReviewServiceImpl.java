package com.project.hrm.services.impl;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.performanceReviewDTO.PerformanceReviewCreateDTO;
import com.project.hrm.dto.performanceReviewDTO.PerformanceReviewDTO;
import com.project.hrm.dto.performanceReviewDTO.PerformanceReviewFilter;
import com.project.hrm.dto.performanceReviewDTO.PerformanceReviewUpdateDTO;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.entities.PerformanceReview;
import com.project.hrm.enums.PerformanceReviewStatus;
import com.project.hrm.enums.ReviewCycle;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.PerformanceReviewMapper;
import com.project.hrm.repositories.PerformanceReviewRepository;
import com.project.hrm.services.AccountService;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.services.PerformanceReviewService;
import com.project.hrm.specifications.PerformanceReviewSpecification;
import com.project.hrm.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class PerformanceReviewServiceImpl implements PerformanceReviewService {
    private final PerformanceReviewRepository performanceReviewRepository;

    private final EmployeeService employeeService;
    private final AccountService accountService;

    private final PerformanceReviewMapper performanceReviewMapper;

    /**
     * Create performance review include: employee, employee request.
     *
     * @param performanceReviewCreateDTO performance review creating
     * @return performance review created {@link PerformanceReviewDTO}
     */
    @Override
    public PerformanceReviewDTO create(PerformanceReviewCreateDTO performanceReviewCreateDTO) {
        log.info("Create performance review");

        Employees employees = employeeService.getEntityById(performanceReviewCreateDTO.getEmployeeId());

        PerformanceReview performanceReview = performanceReviewMapper.convertCreateDTOToEntity(performanceReviewCreateDTO);
        performanceReview.setId(IdGenerator.getGenerationId());
        performanceReview.setEmployee(employees);
        performanceReview.setEmployeeRequest(accountService.getPrincipal());

        return performanceReviewMapper.convertEntityToDTO(performanceReviewRepository.save(performanceReview));
    }

    /**
     * Updates an existing performance review based on the provided update data.
     * This method fetches the entity by ID, applies non-null fields from the DTO,
     * and persists the changes to the database.
     *
     * @param performanceReviewUpdateDTO the DTO containing updated performance review information.
     * @return the updated {@link PerformanceReviewDTO}.
     * @throws CustomException if the performance review or any related employee is not found.
     */
    @Transactional
    @Override
    public PerformanceReviewDTO update(PerformanceReviewUpdateDTO performanceReviewUpdateDTO) {
        log.info("Updating PerformanceReview with ID: {}", performanceReviewUpdateDTO.getId());

        PerformanceReview review = getEntityById(performanceReviewUpdateDTO.getId());

        if(!review.getStatus().name().equals("SCHEDULED")){
            throw new CustomException(Error.PERFORMANCE_REVIEW_UNABLE_TO_UPDATE);
        }

        if (performanceReviewUpdateDTO.getTitle() != null)
            review.setTitle(performanceReviewUpdateDTO.getTitle());

        if (performanceReviewUpdateDTO.getDescription() != null)
            review.setDescription(performanceReviewUpdateDTO.getDescription());

        if (performanceReviewUpdateDTO.getReviewStartDate() != null)
            review.setReviewStartDate(performanceReviewUpdateDTO.getReviewStartDate());

        if (performanceReviewUpdateDTO.getReviewEndDate() != null)
            review.setReviewEndDate(performanceReviewUpdateDTO.getReviewEndDate());

        if (performanceReviewUpdateDTO.getStatus() != null)
            review.setStatus(PerformanceReviewStatus.valueOf(performanceReviewUpdateDTO.getStatus()));

        if (performanceReviewUpdateDTO.getReviewCycle() != null)
            review.setReviewCycle(ReviewCycle.valueOf(performanceReviewUpdateDTO.getReviewCycle()));

        if (performanceReviewUpdateDTO.getOverallComment() != null)
            review.setOverallComment(performanceReviewUpdateDTO.getOverallComment());

        if (performanceReviewUpdateDTO.getOverallScore() != null)
            review.setOverallScore(performanceReviewUpdateDTO.getOverallScore());

        if (performanceReviewUpdateDTO.getEmployeeId() != null) {
            Employees employee = employeeService.getEntityById(performanceReviewUpdateDTO.getEmployeeId());
            review.setEmployee(employee);
        }

        if (performanceReviewUpdateDTO.getEmployeeRequestId() != null) {
            Employees requester = employeeService.getEntityById(performanceReviewUpdateDTO.getEmployeeRequestId());
            review.setEmployeeRequest(requester);
        }

        if (performanceReviewUpdateDTO.getApproverId() != null) {
            review.setApprover(accountService.getPrincipal());
        }

        review.setUpdatedAt(LocalDateTime.now());

        PerformanceReview updated = performanceReviewRepository.save(review);
        log.info("Updated PerformanceReview ID {} successfully.", updated.getId());

        return performanceReviewMapper.convertEntityToDTO(updated);
    }


    /**
     * Find PerformanceReview by Id
     *
     * @param id PerformanceReview Id
     * @return {@link PerformanceReview} entity
     */
    @Transactional(readOnly = true)
    @Override
    public PerformanceReview getEntityById(Integer id) {
        log.info("Find performance review by id: {}", id);

        return performanceReviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.PERFORMANCE_REVIEW_NOT_FOUND));
    }

    /**
     * Find PerformanceReview DTO by Id
     *
     * @param id PerformanceReview Id
     * @return {@link PerformanceReviewDTO} entity
     */
    @Transactional(readOnly = true)
    @Override
    public PerformanceReviewDTO getDTOById(Integer id) {
        return performanceReviewMapper.convertEntityToDTO(getEntityById(id));
    }

    /**
     * Updates the status of a performance review by its ID.
     *
     * @param id     the ID of the performance review to update
     * @param status the new status value (must match {@link PerformanceReviewStatus})
     * @return the updated {@link PerformanceReviewDTO}
     * @throws IllegalArgumentException if the provided status is invalid
     * @throws CustomException if the performance review with given ID is not found
     */
    @Transactional
    @Override
    public PerformanceReviewDTO updateStatus(Integer id, String status) {
        log.info("Updating status of PerformanceReview with ID: {} to {}", id, status);

        PerformanceReview performanceReview = getEntityById(id);

        try {
            performanceReview.setStatus(PerformanceReviewStatus.valueOf(status));
        } catch (IllegalArgumentException ex) {
            log.error("Invalid status '{}' provided for PerformanceReview ID: {}", status, id);
            throw new CustomException(Error.INVALID_ENUM_VALUE);
        }

        PerformanceReview savedReview = performanceReviewRepository.save(performanceReview);
        log.info("Updated status of PerformanceReview ID {} to {}", savedReview.getId(), savedReview.getStatus());

        return performanceReviewMapper.convertEntityToDTO(savedReview);
    }


    /**
     * Filters performance reviews based on multiple optional conditions such as employee ID,
     * approve ID, creation date range, review cycle, and status.
     *
     * @param performanceReviewFilter the filtering conditions
     * @param page                    the page number (0-based)
     * @param size                    the page size
     * @return list of filtered {@link PerformanceReviewDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<PerformanceReviewDTO> filter(PerformanceReviewFilter performanceReviewFilter, int page, int size) {
        log.info("Filtering performance reviews with criteria: {}", performanceReviewFilter);

        Specification<PerformanceReview> specification = PerformanceReviewSpecification.filter(performanceReviewFilter);
        Pageable pageable = PageRequest.of(page, size);

        Page<PerformanceReview> pageResult = performanceReviewRepository.findAll(specification, pageable);

        return performanceReviewMapper.toPerformanceReviewPageDTO(pageResult);
    }

}
