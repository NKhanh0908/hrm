package com.project.hrm.services.impl;

import com.project.hrm.dto.trainingRequestDTO.TrainingRequestCreateDTO;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestDTO;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestFilter;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.TrainingProgram;
import com.project.hrm.entities.TrainingRequest;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.TrainingRequestMapper;
import com.project.hrm.repositories.TrainingRequestRepository;
import com.project.hrm.services.AccountService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.TrainingProgramService;
import com.project.hrm.services.TrainingRequestService;
import com.project.hrm.specifications.TrainingRequestSpecification;
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
public class TrainingRequestServiceImpl implements TrainingRequestService {
    private final TrainingRequestRepository trainingRequestRepository;

    private final EmployeeService employeeService;
    private final TrainingProgramService trainingProgramService;
    private final AccountService accountService;

    private final TrainingRequestMapper trainingRequestMapper;

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
        log.info("Successfully created TrainingRequest with ID: {}", saved.getId());

        return trainingRequestMapper.convertEntityToDTO(saved);
    }

    /**
     * Retrieves a {@link TrainingRequest} entity by its ID.
     *
     * @param id the ID of the training request
     * @return the {@link TrainingRequest} entity
     */
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
    @Override
    public TrainingRequestDTO getDTOById(Integer id) {
        log.info("Fetching TrainingRequestDTO with ID: {}", id);
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
    @Override
    public List<TrainingRequestDTO> filter(TrainingRequestFilter trainingRequestFilter, int page, int size) {
        log.info("Filtering TrainingRequests with filter: {}, page: {}, size: {}",
                trainingRequestFilter, page, size);

        Specification<TrainingRequest> specification = TrainingRequestSpecification.filter(trainingRequestFilter);
        Pageable pageable = PageRequest.of(page, size);
        Page<TrainingRequest> pageResult = trainingRequestRepository.findAll(specification, pageable);

        List<TrainingRequestDTO> dtos = trainingRequestMapper.convertPageToListDTO(pageResult);
        log.info("Found {} training requests matching filter", dtos.size());

        return dtos;
    }
}
