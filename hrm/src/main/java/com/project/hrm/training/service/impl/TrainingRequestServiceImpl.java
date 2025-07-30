package com.project.hrm.training.service.impl;

import com.project.hrm.auth.service.AccountService;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.notification.service.NotificationService;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestCreateDTO;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestDTO;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestFilter;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestUpdateDTO;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.training.entity.TrainingProgram;
import com.project.hrm.training.entity.TrainingRequest;
import com.project.hrm.training.enums.TrainingRequestStatus;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.training.mapper.TrainingRequestMapper;
import com.project.hrm.training.repository.TrainingRequestRepository;
import com.project.hrm.training.specification.TrainingRequestSpecification;
import com.project.hrm.training.service.TrainingEnrollmentService;
import com.project.hrm.training.service.TrainingProgramService;
import com.project.hrm.training.service.TrainingRequestService;
import com.project.hrm.common.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TrainingRequestServiceImpl implements TrainingRequestService {
    private final TrainingRequestRepository trainingRequestRepository;

    private final EmployeeService employeeService;
    private final TrainingProgramService trainingProgramService;
    private final AccountService accountService;
    private final TrainingEnrollmentService trainingEnrollmentService;
    private final NotificationService notificationService;

    private final TrainingRequestMapper trainingRequestMapper;

    public TrainingRequestServiceImpl(
            TrainingRequestRepository trainingRequestRepository,
            EmployeeService employeeService,
            TrainingProgramService trainingProgramService,
            AccountService accountService,
            TrainingRequestMapper trainingRequestMapper,
            @Lazy TrainingEnrollmentService trainingEnrollmentService,
            NotificationService notificationService
    ){
        this.trainingRequestRepository = trainingRequestRepository;
        this.employeeService = employeeService;
        this.trainingProgramService = trainingProgramService;
        this.accountService = accountService;
        this.trainingEnrollmentService = trainingEnrollmentService;
        this.trainingRequestMapper = trainingRequestMapper;
        this.notificationService = notificationService;
    }

    /**
     * Creates a new training request based on the provided data.
     *
     * @param trainingRequestCreateDTO the training request creation data
     * @return the created {@link TrainingRequestDTO}
     */
    @Transactional
    @Override
    public TrainingRequestDTO create(TrainingRequestCreateDTO trainingRequestCreateDTO) {
        log.info("Creating new TrainingRequest with data: {}", trainingRequestCreateDTO);

        Employees targetEmployee = employeeService.getEntityById(trainingRequestCreateDTO.getTargetEmployeeId());
        TrainingProgram trainingProgram = trainingProgramService.getEntityById(trainingRequestCreateDTO.getRequestedProgramId());

        TrainingRequest trainingRequest = trainingRequestMapper.convertCreateDTOToEntity(trainingRequestCreateDTO);
        trainingRequest.setId(IdGenerator.getGenerationId());
        trainingRequest.setTargetEmployee(targetEmployee);
        trainingRequest.setRequestedBy(accountService.getPrincipal());
        trainingRequest.setRequestedProgram(trainingProgram);

        TrainingRequest saved = trainingRequestRepository.save(trainingRequest);

        return trainingRequestMapper.convertEntityToDTO(saved);
    }

    /**
     * Updates an existing training request using the provided update DTO.
     * Only non-null fields in the DTO will be updated.
     *
     * @param trainingRequestUpdateDTO the DTO containing updated training request information
     * @return a {@link TrainingRequestDTO} representing the updated training request
     */
    @Transactional
    @Override
    public TrainingRequestDTO update(TrainingRequestUpdateDTO trainingRequestUpdateDTO) {
        log.info("Update TrainingRequest: {}", trainingRequestUpdateDTO);

        TrainingRequest trainingRequest = getEntityById(trainingRequestUpdateDTO.getId());

        if (trainingRequestUpdateDTO.getReason() != null) {
            trainingRequest.setReason(trainingRequestUpdateDTO.getReason());
        }

        if (trainingRequestUpdateDTO.getExpectedOutcome() != null) {
            trainingRequest.setExpectedOutcome(trainingRequestUpdateDTO.getExpectedOutcome());
        }

        if (trainingRequestUpdateDTO.getPriority() != null) {
            trainingRequest.setPriority(trainingRequestUpdateDTO.getPriority());
        }

        if (trainingRequestUpdateDTO.getStatus() != null) {
            updateStatus(trainingRequest.getId(), trainingRequestUpdateDTO.getStatus());
        }

        if (trainingRequestUpdateDTO.getTargetEmployeeId() != null) {
            Employees employee = employeeService.getEntityById(trainingRequestUpdateDTO.getTargetEmployeeId());
            trainingRequest.setTargetEmployee(employee);
        }

        if (trainingRequestUpdateDTO.getRequestedProgramId() != null) {
            TrainingProgram program = trainingProgramService.getEntityById(trainingRequestUpdateDTO.getRequestedProgramId());
            trainingRequest.setRequestedProgram(program);
        }

        TrainingRequest updatedRequest = trainingRequestRepository.save(trainingRequest);
        return trainingRequestMapper.convertEntityToDTO(updatedRequest);
    }

    /**
    *
    * Update status training request. If Status equal APPROVED then generate Training Enroll with Training session created by id Training program
    * @param id, status. Id training request, status update and add employee update
    * @return a {@link TrainingRequestDTO} representing the updated training request
    *
    * */
    @Transactional
    @Override
    public TrainingRequestDTO updateStatus(Integer id, String status) {
        log.info("Update status TrainingRequest by Id: {}", id);

        TrainingRequest trainingRequest = getEntityById(id);

        trainingRequest.setStatus(TrainingRequestStatus.valueOf(status));
        trainingRequest.setApprovedDate(LocalDateTime.now());
        trainingRequest.setApprovedBy(accountService.getPrincipal());

        TrainingRequestDTO trainingRequestDTO = trainingRequestMapper.convertEntityToDTO(trainingRequestRepository.save(trainingRequest));

        notificationService.sendTrainingRequest(trainingRequestDTO);

        if (trainingRequestDTO.getStatus().name().equals(TrainingRequestStatus.APPROVED.name())){
            trainingEnrollmentService.generateTrainingEnroll(trainingRequestDTO.getRequestedProgramId(), trainingRequest.getId());
        }

        return trainingRequestDTO;
    }

    /**
     * Retrieves a {@link TrainingRequest} entity by its ID.
     *
     * @param id the ID of the training request
     * @return the {@link TrainingRequest} entity
     */
    @Transactional(readOnly = true)
    @Override
    public TrainingRequest getEntityById(Integer id) {
        log.info("Fetching TrainingRequest entity with ID: {}", id);
        return trainingRequestRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("TrainingRequest not found with ID: {}", id);
                    return new CustomException(Error.TRAINING_REQUEST_NOT_FOUND);
                });
    }

    /**
     * Retrieves a {@link TrainingRequestDTO} by its ID.
     *
     * @param id the ID of the training request
     * @return the {@link TrainingRequestDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public TrainingRequestDTO getDTOById(Integer id) {
        return trainingRequestMapper.convertEntityToDTO(getEntityById(id));
    }

    /**
     * Filters training requests based on the provided criteria and pagination parameters.
     *
     * @param trainingRequestFilter the filter criteria
     * @param page                  zero-based page index
     * @param size                  the size of the page to be returned
     * @return a list of matching {@link TrainingRequestDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<TrainingRequestDTO> filter(TrainingRequestFilter trainingRequestFilter, int page, int size) {
        log.info("Filtering TrainingRequests with filter: {}, page: {}, size: {}",
                trainingRequestFilter, page, size);

        Specification<TrainingRequest> specification = TrainingRequestSpecification.filter(trainingRequestFilter);
        Pageable pageable = PageRequest.of(page, size);
        Page<TrainingRequest> pageResult = trainingRequestRepository.findAll(specification, pageable);

        return trainingRequestMapper.toTrainingRequestPageDTO(pageResult);
    }
}
