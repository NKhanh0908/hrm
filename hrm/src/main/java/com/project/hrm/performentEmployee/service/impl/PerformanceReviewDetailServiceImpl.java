package com.project.hrm.performentEmployee.service.impl;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO.PerformanceReviewDetailCreateDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO.PerformanceReviewDetailDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO.PerformanceReviewDetailFilter;
import com.project.hrm.performentEmployee.dto.performanceReviewDetailDTO.PerformanceReviewDetailUpdateDTO;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.performentEmployee.entity.PerformanceReview;
import com.project.hrm.performentEmployee.entity.PerformanceReviewDetail;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.performentEmployee.mapper.PerformanceReviewDetailMapper;
import com.project.hrm.performentEmployee.repository.PerformanceReviewDetailRepository;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.performentEmployee.service.PerformanceReviewDetailService;
import com.project.hrm.performentEmployee.service.PerformanceReviewService;
import com.project.hrm.performentEmployee.specification.PerformanceReviewDetailSpecification;
import com.project.hrm.common.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class PerformanceReviewDetailServiceImpl implements PerformanceReviewDetailService {
    private final PerformanceReviewDetailRepository repo;

    private final PerformanceReviewService performanceReviewService;
    private final EmployeeService employeeService;
    private final AccountService accountService;

    private PerformanceReviewDetailMapper mapper;

    /**
     * Create a new Performance Review Detail
     *
     * @param performanceReviewDetailCreateDTO the DTO containing creation info
     * @return the created PerformanceReviewDetailDTO
     */
    @Transactional
    @Override
    public PerformanceReviewDetailDTO create(PerformanceReviewDetailCreateDTO performanceReviewDetailCreateDTO) {
        log.info("Creating new performance review detail");

        PerformanceReview performanceReview = performanceReviewService.getEntityById(performanceReviewDetailCreateDTO.getPerformanceReviewId());

        PerformanceReviewDetail performanceReviewDetail = mapper.convertCreateDTOToEntity(performanceReviewDetailCreateDTO);
        performanceReviewDetail.setId(IdGenerator.getGenerationId());
        performanceReviewDetail.setReviewer(accountService.getPrincipal());
        performanceReviewDetail.setPerformanceReview(performanceReview);

        return mapper.convertEntityToDTO(repo.save(performanceReviewDetail));
    }

    /**
     * Update existing Performance Review Detail
     *
     * @param performanceReviewDetailCreateDTO DTO with update information
     * @return updated PerformanceReviewDetailDTO
     */
    @Transactional
    @Override
    public PerformanceReviewDetailDTO update(PerformanceReviewDetailUpdateDTO performanceReviewDetailCreateDTO) {
        log.info("Updating performance review detail with id: {}", performanceReviewDetailCreateDTO.getId());

        PerformanceReviewDetail entity = getEntityById(performanceReviewDetailCreateDTO.getId());

        if (performanceReviewDetailCreateDTO.getCriteriaName() != null) entity.setCriteriaName(performanceReviewDetailCreateDTO.getCriteriaName());
        if (performanceReviewDetailCreateDTO.getCriteriaDescription() != null) entity.setCriteriaDescription(performanceReviewDetailCreateDTO.getCriteriaDescription());
        if (performanceReviewDetailCreateDTO.getReviewerId() != null) {
            Employees reviewer = employeeService.getEntityById(performanceReviewDetailCreateDTO.getReviewerId());
            entity.setReviewer(reviewer);
        }
        if (performanceReviewDetailCreateDTO.getComment() != null) entity.setComment(performanceReviewDetailCreateDTO.getComment());
        if (performanceReviewDetailCreateDTO.getScore() != null) entity.setScore(performanceReviewDetailCreateDTO.getScore());
        if (performanceReviewDetailCreateDTO.getPerformanceReviewId() != null) {
            PerformanceReview review = performanceReviewService.getEntityById(performanceReviewDetailCreateDTO.getPerformanceReviewId());
            entity.setPerformanceReview(review);
        }

        return mapper.convertEntityToDTO(repo.save(entity));
    }

    /**
     * Returns a {@link PerformanceReviewDetailDTO} for the given detail ID.
     *
     * @param id the primary key of the performance-review detail
     * @return the corresponding DTO
     * @throws CustomException if no detail record exists with the specified ID
     */
    @Transactional(readOnly = true)
    @Override
    public PerformanceReviewDetailDTO getDTOById(Integer id) {
        log.info("Fetching DTO for performance review detail id: {}", id);
        return mapper.convertEntityToDTO(getEntityById(id));
    }

    /**
     * Returns the {@link PerformanceReviewDetail} entity for the given ID.
     *
     * @param id the primary key of the performance-review detail
     * @return the entity
     * @throws CustomException if the entity is not found
     */
    @Transactional(readOnly = true)
    @Override
    public PerformanceReviewDetail getEntityById(Integer id) {
        log.info("Fetching entity for performance review detail id: {}", id);
        return repo.findById(id).orElseThrow(() -> new CustomException(Error.PERFORMANCE_REVIEW_DETAIL_NOT_FOUND));
    }

    /**
     * Filter performance review details
     *
     * @param filter filter conditions
     * @param page   page index
     * @param size   page size
     * @return list of DTOs
     */
    @Override
    public PageDTO<PerformanceReviewDetailDTO> filter(PerformanceReviewDetailFilter filter, int page, int size) {
        log.info("Filtering performance review details with filter: {}", filter);

        Specification<PerformanceReviewDetail> spec = PerformanceReviewDetailSpecification.filter(filter);
        Pageable pageable = PageRequest.of(page, size);
        Page<PerformanceReviewDetail> result = repo.findAll(spec, pageable);

        return mapper.toReviewDetailPageDTO(result);
    }
}
