package com.project.hrm.recruitment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.notification.dto.NotificationCreateDTO;
import com.project.hrm.notification.enums.SenderType;
import com.project.hrm.notification.service.NotificationService;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementFilter;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementsDTO;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementsUpdateDTO;
import com.project.hrm.recruitment.entity.RecruitmentRequirements;
import com.project.hrm.department.entity.Role;
import com.project.hrm.recruitment.enums.RecruitmentRequirementsStatus;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.recruitment.mapper.RecruitmentRequirementsMapper;
import com.project.hrm.recruitment.repository.RecruitmentRequirementsRepository;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.recruitment.service.RecruitmentRequirementService;
import com.project.hrm.department.service.RoleService;
import com.project.hrm.recruitment.service.RecruitmentService;
import com.project.hrm.recruitment.specification.RecruitmentRequirementsSpecification;
import com.project.hrm.common.utils.IdGenerator;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RecruitmentRequirementServiceImpl implements RecruitmentRequirementService {
    private final RecruitmentRequirementsRepository recruitmentRequirementsRepository;

    private final AccountService accountService;
    private final RoleService roleService;
    private final RecruitmentService recruitmentService;
    private final NotificationService notificationService;

    private final RecruitmentRequirementsMapper recruitmentRequirementsMapper;

    public RecruitmentRequirementServiceImpl(
            RecruitmentRequirementsRepository recruitmentRequirementsRepository,
            AccountService accountService,
            RoleService roleService,
            @Lazy RecruitmentService recruitmentService,
            RecruitmentRequirementsMapper recruitmentRequirementsMapper,
            NotificationService notificationService
    ){
        this.recruitmentRequirementsRepository = recruitmentRequirementsRepository;
        this.accountService = accountService;
        this.roleService = roleService;
        this.recruitmentService = recruitmentService;
        this.recruitmentRequirementsMapper = recruitmentRequirementsMapper;
        this.notificationService = notificationService;
    }

    /**
     * Filters recruitment requirements based on the provided filter criteria with pagination support.
     *
     * @param recruitmentRequirementFilter the filter object containing search criteria such as position, status, date, etc.
     * @param page                         the zero-based page index to retrieve.
     * @param size                         the number of records per page.
     * @return a list of {@link RecruitmentRequirementsDTO} that match the filter criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<RecruitmentRequirementsDTO> filterRecruitmentRequirements(RecruitmentRequirementFilter recruitmentRequirementFilter, int page, int size) {
        log.info("Filter Recruitment Requirements");
        
        Specification<RecruitmentRequirements> recruitmentRequirementsSpecification
                = RecruitmentRequirementsSpecification.filter(recruitmentRequirementFilter);

        Pageable pageable = PageRequest.of(page, size);

        Page<RecruitmentRequirements> recruitmentRequirementsPage
                = recruitmentRequirementsRepository.findAll(recruitmentRequirementsSpecification, pageable);

        return recruitmentRequirementsMapper
                .toRecruitmentRequirementsPageDTO(recruitmentRequirementsPage);
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
                .orElseThrow(() -> new CustomException(Error.RECRUITMENT_REQUIREMENTS_NOT_FOUND));
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

        return recruitmentRequirementsMapper
                .toDTO(getEntityById(id));
    }

    /**
     * Generates a recruitment based on the provided recruitment requirement ID.
     *
     * @param id the ID of the recruitment requirement for which to generate a recruitment.
     */
    @Override
    public void generateRecruitment(Integer id) {
        RecruitmentCreateDTO recruitmentCreateDTO = new RecruitmentCreateDTO();
        recruitmentCreateDTO.setRecruitmentRequirementId(id);

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

        Role role = roleService.getEntityById(recruitmentRequirementsCreateDTO.getRoleId());

        RecruitmentRequirements recruitmentRequirements = recruitmentRequirementsMapper.convertCreateDTOtoEntity(recruitmentRequirementsCreateDTO);
        recruitmentRequirements.setId(IdGenerator.getGenerationId());
        recruitmentRequirements.setRole(role);
        recruitmentRequirements.setRequirements(accountService.getPrincipal());

        return recruitmentRequirementsMapper.toDTO(
                recruitmentRequirementsRepository.save(recruitmentRequirements));
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

        if(!entity.getStatus().equals(RecruitmentRequirementsStatus.PENDING)){
            throw new CustomException(Error.RECRUITMENT_REQUIREMENTS_UNABLE_TO_UPDATE);
        }

        if (recruitmentRequirementsUpdateDTO.getDescription() != null) {
            entity.setDescription(recruitmentRequirementsUpdateDTO.getDescription());
        }

        if (recruitmentRequirementsUpdateDTO.getQuantity() != null) {
            entity.setQuantity(recruitmentRequirementsUpdateDTO.getQuantity());
        }

        if (recruitmentRequirementsUpdateDTO.getExpectedSalary() != null) {
            entity.setExpectedSalary(recruitmentRequirementsUpdateDTO.getExpectedSalary());
        }

        if (recruitmentRequirementsUpdateDTO.getStatus() != null) {
            entity.setStatus(RecruitmentRequirementsStatus.valueOf(recruitmentRequirementsUpdateDTO.getStatus()));
            if(recruitmentRequirementsUpdateDTO.getStatus().equalsIgnoreCase("APPROVED")){
                generateRecruitment(entity.getId());
            }
        }

        if (recruitmentRequirementsUpdateDTO.getRoleId() != null) {
            Role role = roleService.getEntityById(recruitmentRequirementsUpdateDTO.getRoleId());
            entity.setRole(role);
        }

        entity.setRequirements(accountService.getPrincipal());

        return recruitmentRequirementsMapper.toDTO(
                recruitmentRequirementsRepository.save(entity));
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
        log.info("Update status recruitment id, status: {}, {}", id, status.name());

        if (!recruitmentRequirementsRepository.existsById(id)) {
            throw new CustomException(Error.RECRUITMENT_REQUIREMENTS_NOT_FOUND);
        }

        int updated = recruitmentRequirementsRepository.updateStatus(id, status.name());

        if (updated != 1) {
            throw new CustomException(Error.RECRUITMENT_REQUIREMENTS_UNABLE_TO_UPDATE_STATUS);
        }

        RecruitmentRequirements entity = getEntityById(id);

        if(entity.getStatus().equals(RecruitmentRequirementsStatus.APPROVED)){
            generateRecruitment(entity.getId());
        }

        RecruitmentRequirementsDTO recruitmentRequirementsDTO = recruitmentRequirementsMapper.toDTO(entity);

        notificationService.sendRecruitmentRequest(recruitmentRequirementsDTO);

        return recruitmentRequirementsDTO;
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
