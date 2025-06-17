package com.project.hrm.services.impl;

import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentCreateDTO;
import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentDTO;
import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentFilter;
import com.project.hrm.entities.TrainingEnrollment;
import com.project.hrm.entities.TrainingRequest;
import com.project.hrm.entities.TrainingSession;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.TrainingEnrollmentMapper;
import com.project.hrm.repositories.TrainingEnrollmentRepository;
import com.project.hrm.services.*;
import com.project.hrm.specifications.TrainingEnrollmentSpecification;
import com.project.hrm.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TrainingEnrollmentServiceImpl implements TrainingEnrollmentService {
    private final TrainingEnrollmentRepository trainingEnrollmentRepository;

    private final TrainingSessionService trainingSessionService;
    private final TrainingRequestService trainingRequestService;
    private final AccountService accountService;

    private final TrainingEnrollmentMapper trainingEnrollmentMapper;

    /**
     * Creates a new training enrollment based on the provided data.
     *
     * @param trainingEnrollmentCreateDTO the training enrollment creation data
     * @return the created {@link TrainingEnrollmentDTO}
     */
    @Transactional
    @Override
    public TrainingEnrollmentDTO create(TrainingEnrollmentCreateDTO trainingEnrollmentCreateDTO) {
        log.info("Creating new TrainingEnrollment with data: {}", trainingEnrollmentCreateDTO);

        TrainingRequest trainingRequest = trainingRequestService.getEntityById(trainingEnrollmentCreateDTO.getTrainingRequestId());
        TrainingSession trainingSession = trainingSessionService.getEntityById(trainingEnrollmentCreateDTO.getTrainingSessionId());

        TrainingEnrollment trainingEnrollment = trainingEnrollmentMapper.convertCreateDTOToEntity(trainingEnrollmentCreateDTO);
        trainingEnrollment.setId(IdGenerator.getGenerationId());
        trainingEnrollment.setTrainingRequest(trainingRequest);
        trainingEnrollment.setTrainingSession(trainingSession);
        trainingEnrollment.setEmployee(accountService.getPrincipal());

        TrainingEnrollment saved = trainingEnrollmentRepository.save(trainingEnrollment);
        log.info("Successfully created TrainingEnrollment with ID: {}", saved.getId());

        return trainingEnrollmentMapper.convertEntityToDTO(saved);
    }

    /**
     * Retrieves a {@link TrainingEnrollment} entity by its ID.
     *
     * @param id the ID of the training enrollment
     * @return the {@link TrainingEnrollment} entity
     */
    @Override
    public TrainingEnrollment getEntityById(Integer id) {
        log.info("Fetching TrainingEnrollment entity with ID: {}", id);
        return trainingEnrollmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("TrainingEnrollment not found with ID: {}", id);
                    return new CustomException(Error.TRAINING_ENROLLMENT_NOT_FOUND);
                });
    }

    /**
     * Retrieves a {@link TrainingEnrollmentDTO} by its ID.
     *
     * @param id the ID of the training enrollment
     * @return the {@link TrainingEnrollmentDTO}
     */
    @Override
    public TrainingEnrollmentDTO getDTOById(Integer id) {
        log.info("Fetching TrainingEnrollmentDTO with ID: {}", id);
        return trainingEnrollmentMapper.convertEntityToDTO(getEntityById(id));
    }

    /**
     * Filters training enrollments based on the provided criteria and pagination parameters.
     *
     * @param trainingEnrollmentFilter the filter criteria
     * @param page                     zero-based page index
     * @param size                     the size of the page to be returned
     * @return a list of matching {@link TrainingEnrollmentDTO}
     */
    @Override
    public List<TrainingEnrollmentDTO> filter(TrainingEnrollmentFilter trainingEnrollmentFilter, int page, int size) {
        log.info("Filtering TrainingEnrollments with filter: {}, page: {}, size: {}",
                trainingEnrollmentFilter, page, size);

        Specification<TrainingEnrollment> specification = TrainingEnrollmentSpecification.filter(trainingEnrollmentFilter);
        Pageable pageable = PageRequest.of(page, size);
        Page<TrainingEnrollment> pageResult = trainingEnrollmentRepository.findAll(specification, pageable);

        List<TrainingEnrollmentDTO> dtos = trainingEnrollmentMapper.convertPageToListDTO(pageResult);
        log.info("Found {} enrollments matching filter", dtos.size());

        return dtos;
    }
}
