package com.project.hrm.recruitment.service.impl;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.auth.entity.Account;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentFilter;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentUpdateDTO;
import com.project.hrm.recruitment.entity.Recruitment;
import com.project.hrm.recruitment.entity.RecruitmentRequirements;
import com.project.hrm.recruitment.enums.RecruitmentRequirementsStatus;
import com.project.hrm.recruitment.enums.RecruitmentStatus;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.recruitment.mapper.RecruitmentMapper;
import com.project.hrm.recruitment.repository.RecruitmentRepository;
import com.project.hrm.recruitment.service.RecruitmentRequirementService;
import com.project.hrm.recruitment.service.RecruitmentService;
import com.project.hrm.recruitment.specification.RecruitmentSpecification;
import com.project.hrm.common.utils.IdGenerator;
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

@Service
@Slf4j
@AllArgsConstructor
public class RecruitmentServiceImpl implements RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;

    private final RecruitmentMapper recruitmentMapper;

    private final RecruitmentRequirementService recruitmentRequirementService;

    /**
     * Filters recruitment entries based on the given filter criteria and paginates
     * the result.
     *
     * @param recruitmentFilter the object containing filter fields such as
     *                          position, deadline, create at.
     * @param page              the zero-based page index.
     * @param size              the number of records to retrieve per page.
     * @return a list of {@link RecruitmentDTO} that match the filter criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<RecruitmentDTO> filter(RecruitmentFilter recruitmentFilter, int page, int size) {
        log.info("Filter Recruitment");
        Specification<Recruitment> recruitmentSpecification = RecruitmentSpecification.filter(recruitmentFilter);

        Pageable pageable = PageRequest.of(page, size);

        Page<Recruitment> recruitmentPage = recruitmentRepository.findAll(recruitmentSpecification, pageable);

        return recruitmentMapper.toRecruitmentPageDTO(recruitmentPage);
    }

    /**
     * Retrieves the recruitment entity by its ID.
     *
     * @param id the ID of the recruitment record to fetch.
     * @return the {@link Recruitment} entity if found.
     * @throws RuntimeException if no recruitment is found with the specified ID.
     */
    @Transactional(readOnly = true)
    @Override
    public Recruitment getEntityById(Integer id) {
        log.info("Find Entity Recruitment by id: {}", id);

        return recruitmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.RECRUITMENT_NOT_FOUND));
    }

    /**
     * Retrieves the recruitment entry by its ID and converts it to a DTO.
     *
     * @param id the ID of the recruitment record to fetch.
     * @return the {@link RecruitmentDTO} representation of the recruitment record.
     * @throws RuntimeException if no recruitment is found with the specified ID.
     */
    @Transactional(readOnly = true)
    @Override
    public RecruitmentDTO getDTOById(Integer id) {
        log.info("Find Recruitment by id: {}", id);

        return recruitmentMapper.toDTO(getEntityById(id));
    }

    /**
     * Creates a new recruitment record based on the provided data.
     * Retrieves the associated {@link RecruitmentRequirements} entity before
     * mapping to the recruitment entity.
     *
     * @param recruitmentCreateDTO the DTO containing recruitment creation data such
     *                             as position, contact info, and
     *                             recruitmentRequirementId.
     * @return the created {@link RecruitmentDTO}.
     */
    @Transactional
    @Override
    public RecruitmentDTO create(RecruitmentCreateDTO recruitmentCreateDTO) {
        log.info("Create Recruitment");

        RecruitmentRequirements recruitmentRequirements = recruitmentRequirementService
                .getEntityById(recruitmentCreateDTO.getRecruitmentRequirementId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Account principal = (Account) authentication.getPrincipal();

        Recruitment recruitment = recruitmentMapper.convertCreateToEntity(recruitmentCreateDTO, recruitmentRequirements,
                principal.getEmployees());
        recruitment.setId(IdGenerator.getGenerationId());

        return recruitmentMapper.toDTO(recruitmentRepository.save(recruitment));
    }

    /**
     * Approves an internal {@link RecruitmentCreateDTO} record and
     * publishes it publicly by creating a corresponding {@link Recruitment}.
     *
     * @param recruitmentCreateDTO DTO containing approval details
     * @return the {@link RecruitmentDTO} of the newly published position
     * @throws RuntimeException if authentication is missing or any entity is not
     *                          found
     */
    @Transactional
    @Override
    public RecruitmentDTO approved(RecruitmentCreateDTO recruitmentCreateDTO) {
        log.info("Create Requirement after approved Recruitment Requirement");

        recruitmentRequirementService.updateStatus(recruitmentCreateDTO.getRecruitmentRequirementId(), RecruitmentRequirementsStatus.APPROVED);

        return create(recruitmentCreateDTO);
    }

    /**
     * Updates an existing recruitment record with the fields provided in the update
     * DTO.
     * Only non-null fields will be updated. The associated
     * {@link RecruitmentRequirements} will also be updated if provided.
     *
     * @param recruitmentUpdateDTO the DTO containing the updated recruitment data.
     *                             Must include the recruitment ID.
     * @return the updated {@link RecruitmentDTO}.
     * @throws RuntimeException if the recruitment entity is not found with the
     *                          given ID.
     */
    @Transactional
    @Override
    public RecruitmentDTO update(RecruitmentUpdateDTO recruitmentUpdateDTO) {
        log.info("Update Recruitment");

        Recruitment recruitment = getEntityById(recruitmentUpdateDTO.getId());

        if (recruitmentUpdateDTO.getContactPhone() != null) {
            recruitment.setContactPhone(recruitmentUpdateDTO.getContactPhone());
        }

        if (recruitmentUpdateDTO.getEmail() != null) {
            recruitment.setEmail(recruitmentUpdateDTO.getEmail());
        }

        if (recruitmentUpdateDTO.getDeadline() != null) {
            recruitment.setDeadline(recruitmentUpdateDTO.getDeadline());
        }

        if (recruitmentUpdateDTO.getJobDescription() != null) {
            recruitment.setJobDescription(recruitmentUpdateDTO.getJobDescription());
        }

        if (recruitmentUpdateDTO.getRecruitmentRequirementId() != null) {
            RecruitmentRequirements recruitmentRequirements = recruitmentRequirementService
                    .getEntityById(recruitmentUpdateDTO.getRecruitmentRequirementId());
            recruitment.setRecruitmentRequirements(recruitmentRequirements);
        }

        return recruitmentMapper.toDTO(
                recruitmentRepository.save(recruitment));
    }

    /**
     * Updates the status of a recruitment post identified by the given ID.
     *
     * This method first checks for the existence of the recruitment entry.
     * If it exists, it attempts to update the status. If the update fails or no rows are affected,
     * a {@link CustomException} is thrown.
     *
     * @param id the ID of the recruitment post to update
     * @param recruitmentStatus the new {@link RecruitmentStatus} to set
     * @return the updated {@link RecruitmentDTO} after applying the status change
     * @throws CustomException if the recruitment does not exist or the status cannot be updated
     */
    @Transactional
    @Override
    public RecruitmentDTO updateStatus(Integer id, RecruitmentStatus recruitmentStatus) {
        log.info("Update status recruitment status, id: {}, {}", recruitmentStatus.name(), id);

        if(!recruitmentRepository.existsById(id)){
            throw new CustomException(Error.RECRUITMENT_NOT_FOUND);
        }

        int update = recruitmentRepository.updateStatus(id, recruitmentStatus.name());

        if(update!=1){
            throw new CustomException(Error.RECRUITMENT_UNABLE_TO_UPDATE);
        }

        return getDTOById(id);
    }

    /**
     * Deletes a recruitment record by its ID.
     *
     * @param recruitmentId the ID of the recruitment record to be deleted.
     * @throws RuntimeException if no recruitment is found with the given ID.
     */
    @Transactional
    @Override
    public void delete(Integer recruitmentId) {
        log.info("Delete Recruitment");

        Recruitment recruitment = getEntityById(recruitmentId);

        recruitmentRepository.delete(recruitment);

    }

}
