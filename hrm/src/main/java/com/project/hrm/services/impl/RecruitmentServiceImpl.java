package com.project.hrm.services.impl;

import com.project.hrm.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentFilter;
import com.project.hrm.dto.recruitmentDTO.RecruitmentUpdateDTO;
import com.project.hrm.entities.Recruitment;
import com.project.hrm.entities.RecruitmentRequirements;
import com.project.hrm.mapper.RecruitmentMapper;
import com.project.hrm.repositories.RecruitmentRepository;
import com.project.hrm.services.RecruitmentRequirementService;
import com.project.hrm.services.RecruitmentService;
import com.project.hrm.specifications.RecruitmentSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class RecruitmentServiceImpl implements RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentMapper recruitmentMapper;
    private final RecruitmentRequirementService recruitmentRequirementService;

    /**
     * Filters recruitment entries based on the given filter criteria and paginates the result.
     *
     * @param recruitmentFilter the object containing filter fields such as position, deadline, create at.
     * @param page the zero-based page index.
     * @param size the number of records to retrieve per page.
     * @return a list of {@link RecruitmentDTO} that match the filter criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public List<RecruitmentDTO> filter(RecruitmentFilter recruitmentFilter, int page, int size) {
        log.info("Filter Recruitment");
        Specification<Recruitment> recruitmentSpecification
                = RecruitmentSpecification.filter(recruitmentFilter);

        Pageable pageable = PageRequest.of(page, size);

        Page<Recruitment> recruitmentPage
                = recruitmentRepository.findAll(recruitmentSpecification, pageable);

        return recruitmentMapper.convertPageEntityToPageDTO(recruitmentPage);
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
                .orElseThrow(
                        () -> {
                            String message = "Find Recruitment with id " + id + " not found";

                            log.error(message);

                            return new RuntimeException(message);
                        }
                );
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

        return recruitmentMapper.toDTO(
                recruitmentRepository.findById(id).orElseThrow(
                () -> {
                    String message = "Find RecruitmentDTO with id " + id + " not found";

                    log.error(message);

                    return new RuntimeException(message);
                }
        ));
    }

    /**
     * Creates a new recruitment record based on the provided data.
     * Retrieves the associated {@link RecruitmentRequirements} entity before mapping to the recruitment entity.
     *
     * @param recruitmentCreateDTO the DTO containing recruitment creation data such as position, contact info, and recruitmentRequirementId.
     * @return the created {@link RecruitmentDTO}.
     */
    @Transactional
    @Override
    public RecruitmentDTO create(RecruitmentCreateDTO recruitmentCreateDTO) {
        log.info("Create Recruitment");

        RecruitmentRequirements recruitmentRequirements = recruitmentRequirementService.getEntityById(recruitmentCreateDTO.getRecruitmentRequirementId());

        Recruitment recruitment = new Recruitment(recruitmentMapper.convertCreateToEntity(recruitmentCreateDTO, recruitmentRequirements));

        return recruitmentMapper.toDTO(recruitmentRepository.save(recruitment));
    }

    /**
     * Updates an existing recruitment record with the fields provided in the update DTO.
     * Only non-null fields will be updated. The associated {@link RecruitmentRequirements} will also be updated if provided.
     *
     * @param recruitmentUpdateDTO the DTO containing the updated recruitment data. Must include the recruitment ID.
     * @return the updated {@link RecruitmentDTO}.
     * @throws RuntimeException if the recruitment entity is not found with the given ID.
     */
    @Transactional
    @Override
    public RecruitmentDTO update(RecruitmentUpdateDTO recruitmentUpdateDTO) {
        log.info("Update Recruitment");

        Recruitment recruitment = getEntityById(recruitmentUpdateDTO.getId());

        if (recruitmentUpdateDTO.getPosition() != null) {
            recruitment.setPosition(recruitmentUpdateDTO.getPosition());
        }

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

        if (recruitmentUpdateDTO.getRecruitmentRequirementId() != null){
            RecruitmentRequirements recruitmentRequirements = recruitmentRequirementService.getEntityById(recruitmentUpdateDTO.getRecruitmentRequirementId());
            recruitment.setRecruitmentRequirements(recruitmentRequirements);
        }

        return recruitmentMapper.toDTO(
                recruitmentRepository.save(recruitment)
        );
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
