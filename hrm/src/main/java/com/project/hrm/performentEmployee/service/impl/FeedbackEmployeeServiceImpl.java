package com.project.hrm.performentEmployee.service.impl;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO.FeedbackEmployeeCreateDTO;
import com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO.FeedbackEmployeeDTO;
import com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO.FeedbackEmployeeFilter;
import com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO.FeedbackEmployeeUpdateDTO;
import com.project.hrm.performentEmployee.entity.FeedbackEmployee;
import com.project.hrm.performentEmployee.entity.PerformanceReview;
import com.project.hrm.performentEmployee.enums.ReviewType;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.performentEmployee.mapper.FeedbackEmployeeMapper;
import com.project.hrm.performentEmployee.repository.FeedbackEmployeeRepository;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.performentEmployee.service.FeedbackEmployeeService;
import com.project.hrm.performentEmployee.service.PerformanceReviewService;
import com.project.hrm.performentEmployee.specification.FeedbackEmployeeSpecification;
import com.project.hrm.common.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class FeedbackEmployeeServiceImpl implements FeedbackEmployeeService {
    private final FeedbackEmployeeRepository feedbackEmployeeRepository;

    private final PerformanceReviewService performanceReviewService;
    private final AccountService accountService;

    private final FeedbackEmployeeMapper feedbackEmployeeMapper;

    /**
     * Creates new feedback for an employee based on the given DTO.
     *
     * @param feedbackEmployeeCreateDTO the feedback creation details
     * @return the created {@link FeedbackEmployeeDTO}
     */
    @Transactional
    @Override
    public FeedbackEmployeeDTO create(FeedbackEmployeeCreateDTO feedbackEmployeeCreateDTO) {
        log.info("Creating feedback for performance review ID: {}", feedbackEmployeeCreateDTO.getPerformanceReviewId());

        PerformanceReview performanceReview = performanceReviewService.getEntityById(feedbackEmployeeCreateDTO.getPerformanceReviewId());

        FeedbackEmployee feedbackEmployee = feedbackEmployeeMapper.convertCreateDTOToEntity(feedbackEmployeeCreateDTO);
        feedbackEmployee.setId(IdGenerator.getGenerationId());
        feedbackEmployee.setFeedbackProvider(accountService.getPrincipal());
        feedbackEmployee.setPerformanceReview(performanceReview);

        return feedbackEmployeeMapper.convertEntityToDTO(feedbackEmployeeRepository.save(feedbackEmployee));
    }

    /**
     * Updates existing feedback for an employee.
     *
     * @param dto the updated feedback details
     * @return the updated {@link FeedbackEmployeeDTO}
     */
    @Transactional
    @Override
    public FeedbackEmployeeDTO update(FeedbackEmployeeUpdateDTO dto) {
        log.info("Updating feedback with ID: {}", dto.getId());

        FeedbackEmployee feedback = getFeedbackEmployeeById(dto.getId());

        if (dto.getFeedbackContent() != null) feedback.setFeedbackContent(dto.getFeedbackContent());
        if (dto.getStrengths() != null) feedback.setStrengths(dto.getStrengths());
        if (dto.getAreasForImprovement() != null) feedback.setAreasForImprovement(dto.getAreasForImprovement());
        if (dto.getSuggestions() != null) feedback.setSuggestions(dto.getSuggestions());
        if (dto.getFeedbackType() != null) feedback.setFeedbackType(ReviewType.valueOf(dto.getFeedbackType()));
        if (dto.getIsAnonymous() != null) feedback.setIsAnonymous(dto.getIsAnonymous());
        if (dto.getPerformanceReviewId() != null) {
            PerformanceReview review = performanceReviewService.getEntityById(dto.getPerformanceReviewId());
            feedback.setPerformanceReview(review);
        }

        return feedbackEmployeeMapper.convertEntityToDTO(feedbackEmployeeRepository.save(feedback));
    }

    /**
     * Retrieves feedback entity by its ID.
     *
     * @param id feedback ID
     * @return the {@link FeedbackEmployee} entity
     */
    @Transactional(readOnly = true)
    @Override
    public FeedbackEmployee getFeedbackEmployeeById(Integer id) {
        log.info("Fetching feedback entity by ID: {}", id);
        return feedbackEmployeeRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.FEEDBACK_NOT_FOUND));
    }

    /**
     * Retrieves a DTO representation of feedback by its ID.
     *
     * @param id feedback ID
     * @return {@link FeedbackEmployeeDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public FeedbackEmployeeDTO getDTOById(Integer id) {
        log.info("Fetching feedback DTO by ID: {}", id);
        return feedbackEmployeeMapper.convertEntityToDTO(getFeedbackEmployeeById(id));
    }

    /**
     * Filters feedbacks based on given criteria and pagination info.
     *
     * @param filter the filter criteria
     * @param page   page number (0-based)
     * @param size   page size
     * @return list of {@link FeedbackEmployeeDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<FeedbackEmployeeDTO> filter(FeedbackEmployeeFilter filter, int page, int size) {
        log.info("Filtering feedback with filter: {} and page: {}, size: {}", filter, page, size);

        Specification<FeedbackEmployee> spec = FeedbackEmployeeSpecification.filter(filter);
        Page<FeedbackEmployee> result = feedbackEmployeeRepository.findAll(spec, PageRequest.of(page, size));

        return feedbackEmployeeMapper.toFeedbackEmployeePageDTO(result);
    }
}
