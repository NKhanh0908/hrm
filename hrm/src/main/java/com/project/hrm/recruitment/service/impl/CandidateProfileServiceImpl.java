package com.project.hrm.recruitment.service.impl;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileCreateDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileFilter;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileUpdateDTO;
import com.project.hrm.recruitment.entity.CandidateProfile;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.recruitment.mapper.CandidateProfileMapper;
import com.project.hrm.recruitment.repository.CandidateProfileRepository;
import com.project.hrm.recruitment.service.CandidateProfileService;
import com.project.hrm.recruitment.specification.CandidateProfileSpecification;
import com.project.hrm.common.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class CandidateProfileServiceImpl implements CandidateProfileService {
    private final CandidateProfileRepository candidateProfileRepository;

    private final CandidateProfileMapper candidateProfileMapper;

    /**
     * Creates a new candidate profile based on the provided data.
     * Converts the input DTO to an entity, generates a unique ID, and persists it to the database.
     *
     * @param candidateProfileCreateDTO the DTO containing data for the new candidate profile.
     *                                  Must include required fields like name, email, phone, etc.
     * @return the created candidate profile as a {@link CandidateProfileDTO}.
     */
    @Transactional
    @Override
    public CandidateProfileDTO create(CandidateProfileCreateDTO candidateProfileCreateDTO) {
        log.info("Create CandidateProfile");

        CandidateProfile candidateProfile = candidateProfileMapper.convertCreateToEntity(candidateProfileCreateDTO);
        candidateProfile.setId(IdGenerator.getGenerationId());

        return candidateProfileMapper.toCandidateProfileDTO(
                candidateProfileRepository.save(candidateProfile)
        );
    }

    /**
     * Updates an existing candidate profile based on the provided data.
     * Only non-null fields in the update DTO will be applied to the entity.
     *
     * @param dto the DTO containing updated candidate profile data.
     *            Must include the ID of the profile to be updated.
     * @return the updated candidate profile as a {@link CandidateProfileDTO}.
     * @throws RuntimeException if no profile is found with the given ID.
     */
    @Transactional
    @Override
    public CandidateProfileDTO update(CandidateProfileUpdateDTO dto) {
        log.info("Update CandidateProfile");

        CandidateProfile existing = getEntityById(dto.getId());

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            existing.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            existing.setPhone(dto.getPhone());
        }
        if (dto.getLinkCV() != null) {
            existing.setLinkCV(dto.getLinkCV());
        }
        if (dto.getSkills() != null) {
            existing.setSkills(dto.getSkills());
        }
        if (dto.getExperience() != null) {
            existing.setExperience(dto.getExperience());
        }

        CandidateProfile updated = candidateProfileRepository.save(existing);

        return candidateProfileMapper.toCandidateProfileDTO(updated);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Delete CandidateProfile");
    }

    /**
     * Retrieves a candidate profile by its ID and returns it as a DTO.
     *
     * @param id the unique identifier of the candidate profile to retrieve.
     * @return the corresponding {@link CandidateProfileDTO} if found.
     * @throws RuntimeException if no candidate profile is found with the given ID.
     */
    @Transactional(readOnly = true)
    @Override
    public CandidateProfileDTO getById(Integer id) {
        log.info("Find CandidateProfile by id: {}", id);

        return candidateProfileMapper.toCandidateProfileDTO(getEntityById(id));
    }

    /**
     * Filters candidate profiles based on the provided filter criteria, with pagination.
     *
     * @param candidateProfileFilter the filter object containing search criteria such as name, email, position, etc.
     * @param page                   the zero-based page index to retrieve.
     * @param size                   the number of records per page.
     * @return a list of {@link CandidateProfileDTO} that match the filter criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<CandidateProfileDTO> filter(CandidateProfileFilter candidateProfileFilter, int page, int size) {
        log.info("Filter CandidateProfile");

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<CandidateProfile> spec = CandidateProfileSpecification.filter(candidateProfileFilter);

        Page<CandidateProfile> pageResult = candidateProfileRepository.findAll(spec, pageable);

        return candidateProfileMapper.toCandidateProfilePageDTO(pageResult);
    }

    /**
     * Retrieves a candidate profile entity by its ID.
     * This method is useful when the raw entity is needed for internal logic or updates.
     *
     * @param id the unique identifier of the candidate profile.
     * @return the {@link CandidateProfile} entity if found.
     * @throws RuntimeException if no candidate profile is found with the given ID.
     */
    @Transactional(readOnly = true)
    @Override
    public CandidateProfile getEntityById(Integer id) {
        log.info("Find Entity CandidateProfile by id: {}", id);

        return candidateProfileRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.CANDIDATE_PROFILE_NOT_FOUND));
    }

    /**
     * Check exists candidate profile by email
     *
     * @param email check
     * @return CandidateProfile {@link CandidateProfile}
     */
    @Transactional(readOnly = true)
    @Override
    public CandidateProfileDTO checkExistsCandidateProfile(String email) {
        log.info("Check exists CandidateProfile by email: {}", email);

        return candidateProfileMapper.toCandidateProfileDTO(candidateProfileRepository.findCandidateProfileByEmail(email));
    }

    /**
     * Retrieves the candidate profile associated with a specific application ID.
     * This method is marked as read-only transactional to ensure it does not modify the database.
     * It is used to fetch candidate information after applying, typically for hiring or contract creation.
     *
     * @param applyId the ID of the application used to find the candidate profile
     * @return the {@link CandidateProfile} linked to the specified application ID
     */
    @Transactional(readOnly = true)
    @Override
    public CandidateProfile getEntityByApplyId(Integer applyId) {
        log.info("Retrieve candidate profile by apply ID: {}", applyId);

        return candidateProfileRepository.findByApplyId(applyId);
    }


}
