package com.project.hrm.services.impl;

import com.project.hrm.dto.applyDTO.ApplyCreateDTO;
import com.project.hrm.dto.applyDTO.ApplyDTO;
import com.project.hrm.dto.applyDTO.ApplyFilter;
import com.project.hrm.dto.applyDTO.ApplyUpdateDTO;
import com.project.hrm.entities.Apply;
import com.project.hrm.entities.CandidateProfile;
import com.project.hrm.entities.Recruitment;
import com.project.hrm.mapper.ApplyMapper;
import com.project.hrm.repositories.ApplyRepository;
import com.project.hrm.services.ApplyService;
import com.project.hrm.services.CandidateProfileService;
import com.project.hrm.services.RecruitmentService;
import com.project.hrm.specifications.ApplySpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ApplyServiceImpl implements ApplyService {
    private final ApplyRepository applyRepository;
    private final ApplyMapper applyMapper;
    private final RecruitmentService recruitmentService;
    private final CandidateProfileService candidateProfileService;

    /**
     * Creates a new Apply entity using the provided creation DTO.
     *
     * @param applyCreateDTO the DTO containing data to create a new Apply record.
     *                       Includes references to Recruitment and CandidateProfile by ID.
     * @return the created Apply entity as a DTO after being persisted.
     * @throws RuntimeException if referenced Recruitment or CandidateProfile is not found.
     */
    @Transactional
    @Override
    public ApplyDTO create(ApplyCreateDTO applyCreateDTO) {
        log.info("Create Apply");

        Recruitment recruitment = recruitmentService.getEntityById(applyCreateDTO.getRecruitmentId());

        CandidateProfile candidateProfile = candidateProfileService.getEntityById(applyCreateDTO.getCandidateProfileId());

        Apply apply = applyMapper.convertCreateDTOToEntity(applyCreateDTO, recruitment, candidateProfile);

        apply.setId(getGenerationId());
        apply.setApplyAt(LocalDateTime.now());

        return applyMapper.toDTO(applyRepository.save(apply));
    }

    /**
     * Updates an existing Apply entity based on non-null fields in the provided DTO.
     *
     * @param applyUpdateDTO the DTO containing fields to update, including the ID of the Apply.
     * @return the updated Apply entity as a DTO after changes are persisted.
     * @throws RuntimeException if the Apply, Recruitment, or CandidateProfile with the provided ID does not exist.
     */
    @Override
    @Transactional
    public ApplyDTO update(ApplyUpdateDTO applyUpdateDTO) {
        log.info("Update Apply");

        Apply apply = getEntityById(applyUpdateDTO.getId());

        if (applyUpdateDTO.getApplyAt() != null) {
            apply.setApplyAt(applyUpdateDTO.getApplyAt());
        }

        if (applyUpdateDTO.getStatus() != null) {
            apply.setStatus(applyUpdateDTO.getStatus());
        }

        if (applyUpdateDTO.getPosition() != null) {
            apply.setPosition(applyUpdateDTO.getPosition());
        }

        if (applyUpdateDTO.getRecruitmentId() != null) {
            Recruitment recruitment = recruitmentService.getEntityById(applyUpdateDTO.getRecruitmentId());
            apply.setRecruitment(recruitment);
        }

        if (applyUpdateDTO.getCandidateProfileId() != null) {
            CandidateProfile candidateProfile = candidateProfileService.getEntityById(applyUpdateDTO.getCandidateProfileId());
            apply.setCandidateProfile(candidateProfile);
        }

        Apply updated = applyRepository.save(apply);
        return applyMapper.toDTO(updated);
    }

    /**
     * Retrieves an ApplyDTO by its unique ID.
     *
     * @param id the ID of the Apply entity to retrieve.
     * @return the corresponding ApplyDTO.
     * @throws RuntimeException if no Apply entity is found with the given ID.
     */
    @Transactional(readOnly = true)
    @Override
    public ApplyDTO getById(Integer id) {
        log.info("Find Apply by id: {}", id);

        return applyMapper.toDTO(applyRepository.findById(id)
                .orElseThrow()
        );
    }

    /**
     * Retrieves the Apply entity by its ID.
     *
     * @param id the ID of the Apply entity to retrieve.
     * @return the corresponding Apply entity.
     * @throws RuntimeException if no Apply entity is found with the given ID.
     */
    @Transactional(readOnly = true)
    @Override
    public Apply getEntityById(Integer id) {
        log.info("Find Apply entity by id: {}", id);

        return applyRepository.findById(id)
                .orElseThrow();
    }

    /**
     * Filters and retrieves a paginated list of ApplyDTOs based on the provided filter criteria.
     *
     * @param applyFilter the filter criteria including status, position, recruitment ID, etc.
     * @param page the page number to retrieve (0-based).
     * @param size the number of records per page.
     * @return a list of matching ApplyDTOs for the specified page.
     */
    @Transactional(readOnly = true)
    @Override
    public List<ApplyDTO> filter(ApplyFilter applyFilter, int page, int size) {
        log.info("Filter Apply");

        Specification<Apply> applySpecification = ApplySpecification.filter(applyFilter);

        Pageable pageable = PageRequest.of(page, size, Sort.by("applyAt").descending());

        Page<Apply> applyPage = applyRepository.findAll(applySpecification, pageable);

        return applyMapper.convertPageToList(applyPage);
    }

    private Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();

        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
