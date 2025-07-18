package com.project.hrm.training.service.impl;

import com.project.hrm.common.redis.RedisKeys;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.common.service.RedisService;
import com.project.hrm.training.dto.trainingSession.TrainingSessionCreateDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionFilter;
import com.project.hrm.training.dto.trainingSession.TrainingSessionUpdateDTO;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.training.entity.TrainingProgram;
import com.project.hrm.training.entity.TrainingSession;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.training.mapper.TrainingSessionMapper;
import com.project.hrm.training.repository.TrainingSessionRepository;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.training.service.TrainingProgramService;
import com.project.hrm.training.service.TrainingSessionService;
import com.project.hrm.training.specification.TrainingSessionSpecification;
import com.project.hrm.common.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TrainingSessionServiceImpl implements TrainingSessionService {
    private final TrainingSessionRepository trainingSessionRepository;

    private final EmployeeService employeeService;
    private final TrainingProgramService trainingProgramService;
    private final RedisService redisService;

    private final TrainingSessionMapper trainingSessionMapper;

    /**
     * Creates a new training session based on the provided data.
     *
     * @param trainingSessionCreateDTO the training session creation data.
     * @return the created {@link TrainingSessionDTO}.
     */
    @Transactional
    @Override
    public TrainingSessionDTO create(TrainingSessionCreateDTO trainingSessionCreateDTO) {
        log.info("Creating new training session with data: {}", trainingSessionCreateDTO);

        TrainingProgram trainingProgram = trainingProgramService.getEntityById(trainingSessionCreateDTO.getTrainingProgramId());
        Employees employee = employeeService.getEntityById(trainingSessionCreateDTO.getCoordinatorEmployeeId());

        TrainingSession trainingSession = trainingSessionMapper.convertCreateDTOToEntity(trainingSessionCreateDTO);
        trainingSession.setId(IdGenerator.getGenerationId());
        trainingSession.setTrainingProgram(trainingProgram);
        trainingSession.setCoordinator(employee);

        TrainingSession savedSession = trainingSessionRepository.save(trainingSession);
        log.info("Successfully created training session with ID: {}", savedSession.getId());

        redisService.deletePattern("training:sessions:list:*");

        return trainingSessionMapper.convertEntityToDTO(savedSession);
    }

    /**
     * Updates an existing training session with the provided update DTO.
     * Only non-null fields will be applied to the entity.
     *
     * @param trainingSessionUpdateDTO the DTO containing the new training session information
     * @return a {@link TrainingSessionDTO} representing the updated training session
     */
    @Transactional
    @Override
    public TrainingSessionDTO update(TrainingSessionUpdateDTO trainingSessionUpdateDTO) {
        log.info("Update TrainingSession: {}", trainingSessionUpdateDTO);

        TrainingSession session = getEntityById(trainingSessionUpdateDTO.getId());

        if (trainingSessionUpdateDTO.getSessionName() != null) {
            session.setSessionName(trainingSessionUpdateDTO.getSessionName());
        }

        if (trainingSessionUpdateDTO.getDurationHours() != null) {
            session.setDurationHours(trainingSessionUpdateDTO.getDurationHours());
        }

        if (trainingSessionUpdateDTO.getCost() != null) {
            session.setCost(trainingSessionUpdateDTO.getCost());
        }

        if (trainingSessionUpdateDTO.getLocation() != null) {
            session.setLocation(trainingSessionUpdateDTO.getLocation());
        }

        if (trainingSessionUpdateDTO.getMaxParticipants() != null) {
            session.setMaxParticipants(trainingSessionUpdateDTO.getMaxParticipants());
        }

        if (trainingSessionUpdateDTO.getCurrentParticipants() != null) {
            session.setCurrentParticipants(trainingSessionUpdateDTO.getCurrentParticipants());
        }

        if (trainingSessionUpdateDTO.getTrainingProgramId() != null) {
            TrainingProgram trainingProgram = trainingProgramService.getEntityById(trainingSessionUpdateDTO.getTrainingProgramId());
            session.setTrainingProgram(trainingProgram);
        }

        if (trainingSessionUpdateDTO.getCoordinatorId() != null) {
            Employees coordinator = employeeService.getEntityById(trainingSessionUpdateDTO.getCoordinatorId());
            session.setCoordinator(coordinator);
        }

        TrainingSession saved = trainingSessionRepository.save(session);

        redisService.deletePattern("training:sessions:list:*");

        return trainingSessionMapper.convertEntityToDTO(saved);
    }

    /**
     * Retrieves a training session DTO by its ID.
     *
     * @param id the training session ID.
     * @return the corresponding {@link TrainingSessionDTO}.
     */
    @Transactional(readOnly = true)
    @Override
    public TrainingSessionDTO getDTOById(Integer id) {
        return trainingSessionMapper.convertEntityToDTO(getEntityById(id));
    }

    /**
     * Retrieves a training session entity by its ID.
     *
     * @param id the training session ID.
     * @return the corresponding {@link TrainingSession}.
     */
    @Transactional(readOnly = true)
    @Override
    public TrainingSession getEntityById(Integer id) {
        log.info("Fetching training session entity with ID: {}", id);
        return trainingSessionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Training session not found with ID: {}", id);
                    return new CustomException(Error.TRAINING_SESSION_NOT_FOUND);
                });
    }

    /**
     * Retrieves all training sessions associated with the specified training program ID.
     *
     * @param trainingProgramId the ID of the training program to retrieve sessions for
     * @return a list of {@link TrainingSessionDTO} objects representing the sessions
     */
    @Transactional(readOnly = true)
    @Override
    public List<TrainingSessionDTO> getAllByTrainingProgramId(Integer trainingProgramId) {
        log.info("Get all training session by training program id: {}", trainingProgramId);

        return trainingSessionMapper.convertListEntityToListDTO(
                trainingSessionRepository.findAllByTrainingProgramId(trainingProgramId)
        );
    }

    /**
     * Filters training sessions based on provided filter and pagination parameters.
     *
     * @param trainingSessionFilter the filter criteria.
     * @param page                  the page number.
     * @param size                  the page size.
     * @return list of matching {@link TrainingSessionDTO}.
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<TrainingSessionDTO> filter(TrainingSessionFilter trainingSessionFilter, int page, int size) {
        log.info("Filtering training sessions with filter: {}, page: {}, size: {}", trainingSessionFilter, page, size);

        String cacheKey = String.format("%s:page:%d:size:%d:filter:%s",
                RedisKeys.TRAINING_SESSIONS_LIST, page, size, trainingSessionFilter.toString());

        PageDTO<TrainingSessionDTO> cache = redisService.get(cacheKey, PageDTO.class);
        if(cache != null) {
            log.info("Retrieved training session from cache: {}", cacheKey);
            return cache;
        }

        Specification<TrainingSession> trainingSessionSpecification = TrainingSessionSpecification.filter(trainingSessionFilter);

        Pageable pageable = PageRequest.of(page, size);

        Page<TrainingSession> trainingSessionPage = trainingSessionRepository.findAll(trainingSessionSpecification, pageable);

        PageDTO<TrainingSessionDTO> result = trainingSessionMapper.toTrainingSessionPageDTO(trainingSessionPage);

        redisService.set(cacheKey, result, Duration.ofMinutes(10));

        return result;
    }
}
