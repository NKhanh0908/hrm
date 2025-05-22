package com.project.hrm.services.impl;

import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementFilter;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsUpdateDTO;
import com.project.hrm.entities.Account;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.RecruitmentRequirements;
import com.project.hrm.enums.RecruitmentRequirementsStatus;
import com.project.hrm.mapper.RecruitmentRequirementsMapper;
import com.project.hrm.repositories.RecruitmentRequirementsRepository;
import com.project.hrm.services.DepartmentService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.RecruitmentRequirementService;
import com.project.hrm.specifications.RecruitmentRequirementsSpecification;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RecruitmentRequirementServiceImpl implements RecruitmentRequirementService {
    private final RecruitmentRequirementsRepository recruitmentRequirementsRepository;
    private final RecruitmentRequirementsMapper recruitmentRequirementsMapper;
    private final DepartmentService departmentService;

    /**
     * Filters recruitment requirements based on the provided filter criteria with pagination support.
     *
     * @param recruitmentRequirementFilter the filter object containing search criteria such as position, status, date, etc.
     * @param page the zero-based page index to retrieve.
     * @param size the number of records per page.
     * @return a list of {@link RecruitmentRequirementsDTO} that match the filter criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public List<RecruitmentRequirementsDTO> filterRecruitmentRequirements(RecruitmentRequirementFilter recruitmentRequirementFilter, int page, int size) {
        log.info("Filter Recruitment Requirements");
        
        Specification<RecruitmentRequirements> recruitmentRequirementsSpecification
                = RecruitmentRequirementsSpecification.filter(recruitmentRequirementFilter);

        Pageable pageable = PageRequest.of(page, size);

        Page<RecruitmentRequirements> recruitmentRequirementsPage
                = recruitmentRequirementsRepository.findAll(recruitmentRequirementsSpecification, pageable);

        return recruitmentRequirementsMapper
                .toPageEntityToPageDTO(recruitmentRequirementsPage);
    }

    /**
     * Retrieves a recruitment requirement entity by its ID.
     * This is typically used when raw entity access is needed for further processing.
     *
     * @param id the unique identifier of the recruitment requirement.
     * @return the {@link RecruitmentRequirements} entity if found.
     * @throws RuntimeException if no recruitment requirement is found with the given ID.
     */
    @Transactional(readOnly = true)
    @Override
    public RecruitmentRequirements getEntityById(Integer id) {
        log.info("Find RecruitmentRequirements by id: {}", id);

        return recruitmentRequirementsRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Find RecruitmentRequirements with id " + id + " not found";

                    log.error(message);

                    return new RuntimeException(message);
                });
    }

    /**
     * Retrieves a recruitment requirement by its ID and returns it as a DTO.
     *
     * @param id the unique identifier of the recruitment requirement.
     * @return the corresponding {@link RecruitmentRequirementsDTO} if found.
     * @throws RuntimeException if no recruitment requirement is found with the given ID.
     */
    @Transactional(readOnly = true)
    @Override
    public RecruitmentRequirementsDTO getDTOById(Integer id) {
        log.info("Find RecruitmentRequirementsDTO by id: {}", id);

        RecruitmentRequirements recruitmentRequirements
                = recruitmentRequirementsRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Find RecruitmentRequirementsDTO with id " + id + " not found";

                    log.error(message);

                    return new RuntimeException(message);
                });

        return recruitmentRequirementsMapper
                .toDTO(recruitmentRequirements);
    }

    /**
     * Creates a new recruitment requirement based on the provided data.
     * Fetches related department and employee entities to build the relationship before saving.
     *
     * @param recruitmentRequirementsCreateDTO the DTO containing the recruitment requirement data to be created.
     *                                         Must include fields such as departmentId, createdBy, description, etc.
     * @return the created {@link RecruitmentRequirementsDTO}.
     */
    @Transactional
    @Override
    public RecruitmentRequirementsDTO create(RecruitmentRequirementsCreateDTO recruitmentRequirementsCreateDTO) {
        log.info("Create RecruitmentRequirements");

        Departments departments = departmentService.getEntityById(recruitmentRequirementsCreateDTO.getDepartmentId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Account account = (Account) authentication.getPrincipal();

        RecruitmentRequirements recruitmentRequirements = recruitmentRequirementsMapper.convertCreateDTOtoEntity(recruitmentRequirementsCreateDTO, departments, account.getEmployees());
        recruitmentRequirements.setId(IdGenerator.getGenerationId());

        return recruitmentRequirementsMapper.toDTO(
                recruitmentRequirementsRepository.save(recruitmentRequirements)
        );
    }

    /**
     * Updates an existing recruitment requirement based on the provided data.
     * Only non-null fields in the update DTO will be applied to the entity.
     * Related department and employee references will also be updated if provided.
     *
     * @param recruitmentRequirementsUpdateDTO the DTO containing updated data.
     *                                         Must include the ID of the recruitment requirement to update.
     * @return the updated {@link RecruitmentRequirementsDTO}.
     * @throws RuntimeException if no recruitment requirement is found with the given ID.
     */
    @Transactional
    @Override
    public RecruitmentRequirementsDTO update(RecruitmentRequirementsUpdateDTO recruitmentRequirementsUpdateDTO) {
        log.info("Update RecruitmentRequirements");

        RecruitmentRequirements entity = getEntityById(recruitmentRequirementsUpdateDTO.getId());

        if (recruitmentRequirementsUpdateDTO.getDescription() != null) {
            entity.setDescription(recruitmentRequirementsUpdateDTO.getDescription());
        }

        if (recruitmentRequirementsUpdateDTO.getPositions() != null) {
            entity.setPositions(recruitmentRequirementsUpdateDTO.getPositions());
        }

        if (recruitmentRequirementsUpdateDTO.getQuantity() != null) {
            entity.setQuantity(recruitmentRequirementsUpdateDTO.getQuantity());
        }

        if (recruitmentRequirementsUpdateDTO.getExpectedSalary() != null) {
            entity.setExpectedSalary(recruitmentRequirementsUpdateDTO.getExpectedSalary());
        }

        if (recruitmentRequirementsUpdateDTO.getStatus() != null) {
            entity.setStatus(RecruitmentRequirementsStatus.valueOf(recruitmentRequirementsUpdateDTO.getStatus()));
        }

        if (recruitmentRequirementsUpdateDTO.getDepartmentId() != null) {
            Departments department = departmentService.getEntityById(recruitmentRequirementsUpdateDTO.getDepartmentId());
            entity.setDepartments(department);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.info("if auth");
            return null;
        }

        log.info("authentication.getPrincipal(): {}", authentication.getPrincipal());
        Account account = (Account) authentication.getPrincipal();

            entity.setEmployees(account.getEmployees());

        return recruitmentRequirementsMapper.toDTO(
                recruitmentRequirementsRepository.save(entity)
        );
    }

    /**
     * Change the status on an existing RecruitmentRequirements.
     *
     * @param id     the requirement ID
     * @param status the new {@link RecruitmentRequirementsStatus}
     * @return the updated {@link RecruitmentRequirementsDTO}
     * @throws EntityNotFoundException if none exists with the given ID
     */
    @Transactional
    @Override
    public RecruitmentRequirementsDTO updateStatus(Integer id, RecruitmentRequirementsStatus status) {
        if (!recruitmentRequirementsRepository.existsById(id)) {
            throw new EntityNotFoundException("Requirement not found: " + id);
        }
        int updated = recruitmentRequirementsRepository.updateStatus(id, status.name());
        if (updated != 1) {
            throw new IllegalStateException("Failed to update status for requirement ID " + id);
        }
        RecruitmentRequirements entity = recruitmentRequirementsRepository.findById(id).orElseThrow();
        return recruitmentRequirementsMapper.toDTO(entity);
    }

    /**
     * Deletes a recruitment requirement by its ID.
     *
     * @param requirementId the ID of the recruitment requirement to delete.
     * @throws RuntimeException if no recruitment requirement is found with the given ID.
     */
    @Transactional
    @Override
    public void delete(Integer requirementId) {
        log.info("Delete RecruitmentRequirements");

        RecruitmentRequirements recruitmentRequirements
                = getEntityById(requirementId);

        recruitmentRequirementsRepository.delete(recruitmentRequirements);
    }

}
