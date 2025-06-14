package com.project.hrm.services.impl;

import com.project.hrm.dto.trainingProgramDTO.TrainingProgramCreateDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramFilter;
import com.project.hrm.entities.Account;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.TrainingProgram;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.TrainingProgramMapper;
import com.project.hrm.repositories.TrainingProgramRepository;
import com.project.hrm.services.DepartmentService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.TrainingProgramService;
import com.project.hrm.specifications.TrainingProgramSpecification;
import com.project.hrm.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TrainingProgramServiceImpl implements TrainingProgramService {
    private final TrainingProgramRepository trainingProgramRepository;

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    private final TrainingProgramMapper trainingProgramMapper;

    /**
     * Creates a new training program based on the provided data.
     *
     * @param trainingProgramCreateDTO the training program creation data.
     * @return the created {@link TrainingProgramDTO}.
     */
    @Transactional
    @Override
    public TrainingProgramDTO create(TrainingProgramCreateDTO trainingProgramCreateDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(Error.UNAUTHORIZED);
        }
        Account account = (Account) authentication.getPrincipal();

        Departments departments = departmentService.getEntityById(trainingProgramCreateDTO.getDepartmentId());

        TrainingProgram trainingProgram = trainingProgramMapper.convertCreateDTOToEntity(trainingProgramCreateDTO);

        trainingProgram.setId(IdGenerator.getGenerationId());
        trainingProgram.setCreateBy(account.getEmployees());
        trainingProgram.setDepartments(departments);

        return trainingProgramMapper.convertToDTO(trainingProgramRepository.save(trainingProgram));
    }

    /**
     * Retrieves a {@link TrainingProgram} entity by its ID.
     *
     * @param id the ID of the training program.
     * @return the found {@link TrainingProgram} entity.
     */
    @Transactional(readOnly = true)
    @Override
    public TrainingProgram getEntityById(Integer id) {
        return trainingProgramRepository.findById(id)
                .orElseThrow();
    }

    /**
     * Retrieves a {@link TrainingProgramDTO} by its ID.
     *
     * @param id the ID of the training program.
     * @return the found {@link TrainingProgramDTO}.
     */
    @Transactional(readOnly = true)
    @Override
    public TrainingProgramDTO getDTOById(Integer id) {
        return trainingProgramMapper.convertToDTO(getEntityById(id));
    }

    /**
     * Filters training programs based on filter criteria and paginates the result.
     *
     * @param trainingProgramFilter the object containing filter fields.
     * @param page                  the zero-based page index.
     * @param size                  the number of records per page.
     * @return list of {@link TrainingProgramDTO} matching the filter.
     */
    @Transactional(readOnly = true)
    @Override
    public List<TrainingProgramDTO> filter(TrainingProgramFilter trainingProgramFilter, int page, int size) {
        Specification<TrainingProgram> trainingProgramSpecification = TrainingProgramSpecification.filter((trainingProgramFilter));

        Pageable pageable = PageRequest.of(page, size);

        Page<TrainingProgram> trainingProgramPage = trainingProgramRepository.findAll(trainingProgramSpecification, pageable);

        return trainingProgramMapper.convertPageToListDTO(trainingProgramPage);
    }
}
