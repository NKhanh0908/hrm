package com.project.hrm.training.service.impl;

import com.project.hrm.auth.service.AccountService;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.training.dto.trainingEnrollmentDTO.TrainingEnrollmentCreateDTO;
import com.project.hrm.training.dto.trainingEnrollmentDTO.TrainingEnrollmentDTO;
import com.project.hrm.training.dto.trainingEnrollmentDTO.TrainingEnrollmentFilter;
import com.project.hrm.training.dto.trainingEnrollmentDTO.TrainingEnrollmentUpdateDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionDTO;
import com.project.hrm.training.entity.TrainingEnrollment;
import com.project.hrm.training.entity.TrainingRequest;
import com.project.hrm.training.entity.TrainingSession;
import com.project.hrm.training.enums.EnrollmentStatus;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.training.mapper.TrainingEnrollmentMapper;
import com.project.hrm.training.repository.TrainingEnrollmentRepository;
import com.project.hrm.training.specification.TrainingEnrollmentSpecification;
import com.project.hrm.training.service.TrainingEnrollmentService;
import com.project.hrm.training.service.TrainingRequestService;
import com.project.hrm.training.service.TrainingSessionService;
import com.project.hrm.common.utils.IdGenerator;
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
     * Updates an existing training enrollment record.
     * Only the non-null fields present in {@link TrainingEnrollmentUpdateDTO}
     * will be applied to the entity.
     *
     * @param trainingEnrollmentUpdateDTO DTO carrying the fields to be updated
     * @return the updated {@link TrainingEnrollmentDTO}
     * @throws CustomException if the enrollment record is not found
     * @throws IllegalArgumentException if the status string cannot be converted to {@link EnrollmentStatus}
     */
    @Transactional
    @Override
    public TrainingEnrollmentDTO update(TrainingEnrollmentUpdateDTO trainingEnrollmentUpdateDTO) {

        log.info("Updating TrainingEnrollment with ID: {}", trainingEnrollmentUpdateDTO.getId());

        TrainingEnrollment enrollment = getEntityById(trainingEnrollmentUpdateDTO.getId());

        if (trainingEnrollmentUpdateDTO.getEnrollmentDate() != null) {
            enrollment.setEnrollmentDate(trainingEnrollmentUpdateDTO.getEnrollmentDate());
        }
        if (trainingEnrollmentUpdateDTO.getCompletionDate() != null) {
            enrollment.setCompletionDate(trainingEnrollmentUpdateDTO.getCompletionDate());
        }
        if (trainingEnrollmentUpdateDTO.getAttendanceRate() != null) {
            enrollment.setAttendanceRate(trainingEnrollmentUpdateDTO.getAttendanceRate());
        }
        if (trainingEnrollmentUpdateDTO.getTestScore() != null) {
            enrollment.setTestScore(trainingEnrollmentUpdateDTO.getTestScore());
        }
        if (trainingEnrollmentUpdateDTO.getFeedback() != null) {
            enrollment.setFeedback(trainingEnrollmentUpdateDTO.getFeedback());
        }
        if (trainingEnrollmentUpdateDTO.getStatus() != null) {
            try {
                enrollment.setStatus(EnrollmentStatus.valueOf(
                        trainingEnrollmentUpdateDTO.getStatus().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                log.error("Invalid status {} supplied for TrainingEnrollment ID {}",
                        trainingEnrollmentUpdateDTO.getStatus(),
                        trainingEnrollmentUpdateDTO.getId());
                throw ex;
            }
        }

        TrainingEnrollment updated = trainingEnrollmentRepository.save(enrollment);
        log.info("TrainingEnrollment ID {} updated successfully", updated.getId());

        return trainingEnrollmentMapper.convertEntityToDTO(updated);
    }

    /**
     * Updates only the status of a training-enrollment record.
     *
     * @param id     the ID of the training enrollment to update
     * @param status the new status (must be a valid {@link EnrollmentStatus})
     * @return the updated {@link TrainingEnrollmentDTO}
     * @throws CustomException          if the enrollment is not found
     * @throws IllegalArgumentException if the status string is not a valid enum constant
     */
    @Transactional
    @Override
    public TrainingEnrollmentDTO updateStatus(Integer id, String status) {
        log.info("Updating status of TrainingEnrollment ID {} to {}", id, status);

        TrainingEnrollment trainingEnrollment = getEntityById(id);

        try {
            trainingEnrollment.setStatus(EnrollmentStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException ex) {
            log.error("Invalid status '{}' supplied for TrainingEnrollment ID {}", status, id);
            throw ex;
        }

        TrainingEnrollment saved = trainingEnrollmentRepository.save(trainingEnrollment);
        log.info("TrainingEnrollment ID {} status updated to {}", saved.getId(), saved.getStatus());

        if(!trainingEnrollmentRepository.existsActiveEnrollment(saved.getTrainingSession().getId(), saved.getTrainingRequest().getId())){
            trainingRequestService.updateStatus(saved.getTrainingRequest().getId(), "FULFILLED");
        }

        return trainingEnrollmentMapper.convertEntityToDTO(saved);
    }

    /**
     * Generate training enrollment entries for all sessions under a specific training program.
     *
     * This method retrieves all training sessions associated with the given requested program ID,
     * and for each session, it creates a new training enrollment linked to the provided training request ID.
     *
     * @param requestedProgramId the ID of the training program to fetch sessions from
     * @param trainingRequest the ID of the training request to associate with the enrollment
     * @return a list of {@link TrainingEnrollmentDTO} objects representing the generated enrollments
     */
    @Transactional
    @Override
    public List<TrainingEnrollmentDTO> generateTrainingEnroll(Integer requestedProgramId, Integer trainingRequest) {
        List<TrainingSessionDTO> trainingSessionDTOS = trainingSessionService.getAllByTrainingProgramId(requestedProgramId);

        return trainingSessionDTOS.stream()
                .map(trainingSessionDTO -> {
                    TrainingEnrollmentCreateDTO trainingEnrollmentCreateDTO = new TrainingEnrollmentCreateDTO();
                    trainingEnrollmentCreateDTO.setTrainingRequestId(trainingRequest);
                    trainingEnrollmentCreateDTO.setTrainingSessionId(trainingSessionDTO.getTrainingProgramId());
                    trainingEnrollmentCreateDTO.setStatus("ENROLLED");

                    return create(trainingEnrollmentCreateDTO);
                })
                .toList();
    }

    /**
     * Retrieves a {@link TrainingEnrollment} entity by its ID.
     *
     * @param id the ID of the training enrollment
     * @return the {@link TrainingEnrollment} entity
     */
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    @Override
    public PageDTO<TrainingEnrollmentDTO> filter(TrainingEnrollmentFilter trainingEnrollmentFilter, int page, int size) {
        log.info("Filtering TrainingEnrollments with filter: {}, page: {}, size: {}",
                trainingEnrollmentFilter, page, size);

        Specification<TrainingEnrollment> specification = TrainingEnrollmentSpecification.filter(trainingEnrollmentFilter);
        Pageable pageable = PageRequest.of(page, size);
        Page<TrainingEnrollment> pageResult = trainingEnrollmentRepository.findAll(specification, pageable);

        return trainingEnrollmentMapper.toTrainingEnrollmentPageDTO(pageResult);
    }
}
