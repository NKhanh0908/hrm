package com.project.hrm.services.impl;

import com.project.hrm.dto.trainingProgramDTO.TrainingProgramCreateDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramFilter;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramUpdateDTO;
import com.project.hrm.entities.Account;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Role;
import com.project.hrm.entities.TrainingProgram;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.TrainingProgramMapper;
import com.project.hrm.repositories.TrainingProgramRepository;
import com.project.hrm.services.*;
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

    private final RoleService roleService;
    private final AccountService accountService;

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

        TrainingProgram trainingProgram = trainingProgramMapper.convertCreateDTOToEntity(trainingProgramCreateDTO);

        Role role = roleService.getEntityById(trainingProgramCreateDTO.getRoleId());

        trainingProgram.setId(IdGenerator.getGenerationId());
        trainingProgram.setCreateBy(accountService.getPrincipal());
        trainingProgram.setTargetRole(role);

        return trainingProgramMapper.convertToDTO(trainingProgramRepository.save(trainingProgram));
    }

    /**
     * Updates an existing training program based on the information provided in {@link TrainingProgramUpdateDTO}.
     * This method retrieves the entity, applies non-null changes from the DTO, and persists the updated entity.
     *
     * @param trainingProgramUpdateDTO the DTO containing the fields to be updated (must include the program ID)
     * @return a {@link TrainingProgramDTO} representing the training program after the update
     */
    @Transactional
    @Override
    public TrainingProgramDTO update(TrainingProgramUpdateDTO trainingProgramUpdateDTO) {
        log.info("Updating TrainingProgram with ID: {}", trainingProgramUpdateDTO.getId());

        TrainingProgram program = getEntityById(trainingProgramUpdateDTO.getId());

        if (trainingProgramUpdateDTO.getTitle() != null) {
            program.setTitle(trainingProgramUpdateDTO.getTitle());
        }
        if (trainingProgramUpdateDTO.getDescription() != null) {
            program.setDescription(trainingProgramUpdateDTO.getDescription());
        }
        if (trainingProgramUpdateDTO.getCreateAt() != null) {
            program.setCreateAt(trainingProgramUpdateDTO.getCreateAt());
        }
        if (trainingProgramUpdateDTO.getMaterials() != null) {
            program.setMaterials(trainingProgramUpdateDTO.getMaterials());
        }
        if (trainingProgramUpdateDTO.getPrerequisites() != null) {
            program.setPrerequisites(trainingProgramUpdateDTO.getPrerequisites());
        }
        if (trainingProgramUpdateDTO.getIsMandatory() != null) {
            program.setIsMandatory(trainingProgramUpdateDTO.getIsMandatory());
        }

        TrainingProgram updated = trainingProgramRepository.save(program);
        return trainingProgramMapper.convertToDTO(updated);
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
                .orElseThrow(() -> new CustomException(Error.TRAINING_PROGRAM_NOT_FOUND));
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
