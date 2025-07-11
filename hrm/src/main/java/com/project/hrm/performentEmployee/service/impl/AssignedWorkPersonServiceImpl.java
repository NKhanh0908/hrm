package com.project.hrm.performentEmployee.service.impl;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.assignedWorkPersonDTO.AssignedWorkPersonCreateDTO;
import com.project.hrm.performentEmployee.dto.assignedWorkPersonDTO.AssignedWorkPersonDTO;
import com.project.hrm.performentEmployee.dto.assignedWorkPersonDTO.AssignedWorkPersonUpdateDTO;
import com.project.hrm.performentEmployee.entity.AssignedWorkPerson;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.performentEmployee.mapper.AssignedWorkPersonMapper;
import com.project.hrm.performentEmployee.repository.AssignedWorkPersonRepository;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.performentEmployee.service.AssignedWorkPersonService;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.common.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class AssignedWorkPersonServiceImpl implements AssignedWorkPersonService {
    private final AssignedWorkPersonRepository repo;

    private final EmployeeService employeeService;
    private final AccountService accountService;

    private final AssignedWorkPersonMapper mapper;

    /**
     * Creates a new AssignedWorkPerson linking a person to assigned work.
     *
     * @param dto the creation DTO containing work and person IDs
     * @return the created {@link AssignedWorkPersonDTO}
     */
    @Transactional
    @Override
    public AssignedWorkPersonDTO create(AssignedWorkPersonCreateDTO dto) {
        log.info("Creating AssignedWorkPerson: {}", dto);

        Employees person = employeeService.getEntityById(dto.getEmployeeId());

        AssignedWorkPerson entity = mapper.convertCreateDTOToEntity(dto);
        entity.setId(IdGenerator.getGenerationId());
        entity.setEmployee(person);
        entity.setAssignedBy(accountService.getPrincipal());

        return mapper.convertEntityToDTO(repo.save(entity));
    }

    /**
     * Updates an existing AssignedWorkPerson with provided details.
     * Only non-null fields in the DTO will be applied.
     *
     * @param dto the update DTO containing new values
     * @return the updated {@link AssignedWorkPersonDTO}
     */
    @Transactional
    @Override
    public AssignedWorkPersonDTO update(AssignedWorkPersonUpdateDTO dto) {
        log.info("Updating AssignedWorkPerson with ID: {}", dto.getId());

        AssignedWorkPerson entity = getEntityById(dto.getId());
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getStartDate() != null) entity.setStartDate(dto.getStartDate());
        if (dto.getTargetDate() != null) entity.setTargetDate(dto.getTargetDate());
        if (dto.getCompletedDate() != null) entity.setCompletedDate(dto.getCompletedDate());
        if (dto.getProgressPercentage() != null) entity.setProgressPercentage(dto.getProgressPercentage());
        if (dto.getProgressNotes() != null) entity.setProgressNotes(dto.getProgressNotes());
        if (dto.getEmployeeId() != null) {
            Employees person = employeeService.getEntityById(dto.getEmployeeId());
            entity.setEmployee(person);
        }

        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.convertEntityToDTO(repo.save(entity));
    }

    /**
     * Retrieves the AssignedWorkPerson entity by its ID.
     *
     * @param id the entity ID
     * @return the {@link AssignedWorkPerson} entity
     */
    @Transactional(readOnly = true)
    @Override
    public AssignedWorkPerson getEntityById(Integer id) {
        log.info("Fetching AssignedWorkPerson entity by ID: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> new CustomException(Error.ASSIGNED_WORK_PERSON_NOT_FOUND));
    }

    /**
     * Retrieves the AssignedWorkPerson DTO by its ID.
     *
     * @param id the entity ID
     * @return the {@link AssignedWorkPersonDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public AssignedWorkPersonDTO getDtoById(Integer id) {
        log.info("Fetching AssignedWorkPerson DTO by ID: {}", id);
        return mapper.convertEntityToDTO(getEntityById(id));
    }

    /**
     * Filters AssignedWorkPerson records based on criteria and pagination.
     *
     * @param employeeId the filtering criteria
     * @param page
     * @param size
     * @return list of {@link AssignedWorkPersonDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<AssignedWorkPersonDTO> filterByEmployeeId(Integer employeeId, int page, int size) {
        log.info("Filtering AssignedWorkPerson by employeeId={}, page={}, size={}", employeeId, page, size);

        Page<AssignedWorkPerson> entityPage =
                repo.findAllByEmployeeId(employeeId, PageRequest.of(page, size));

        return mapper.toAssignedWorkPersonPageDTO(entityPage);
    }
}
