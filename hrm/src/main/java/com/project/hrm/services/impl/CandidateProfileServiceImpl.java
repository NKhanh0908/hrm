package com.project.hrm.services.impl;

import com.project.hrm.dto.candidateProfileDTO.CandidateProfileCreateDTO;
import com.project.hrm.dto.candidateProfileDTO.CandidateProfileDTO;
import com.project.hrm.dto.candidateProfileDTO.CandidateProfileFilter;
import com.project.hrm.dto.candidateProfileDTO.CandidateProfileUpdateDTO;
import com.project.hrm.entities.CandidateProfile;
import com.project.hrm.mapper.CandidateProfileMapper;
import com.project.hrm.repositories.CandidateProfileRepository;
import com.project.hrm.services.CandidateProfileService;
import com.project.hrm.specifications.CandidateProfileSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CandidateProfileServiceImpl implements CandidateProfileService {
    private final CandidateProfileRepository candidateProfileRepository;
    private final CandidateProfileMapper candidateProfileMapper;

    @Transactional
    @Override
    public CandidateProfileDTO create(CandidateProfileCreateDTO candidateProfileCreateDTO) {
        log.info("Create CandidateProfile");

        CandidateProfile candidateProfile=candidateProfileMapper.convertCreateToEntity(candidateProfileCreateDTO);

        candidateProfile.setId(getGenerationId());

        return candidateProfileMapper.toCandidateProfileDTO(
                candidateProfileRepository.save(candidateProfile)
        );
    }

    @Transactional
    @Override
    public CandidateProfileDTO update(CandidateProfileUpdateDTO dto) {
        log.info("Update CandidateProfile");

        CandidateProfile existing = candidateProfileRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("CandidateProfile not found with ID: " + dto.getId()));

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
        if (dto.getCreateProfileAt() != null) {
            existing.setCreateProfileAt(dto.getCreateProfileAt());
        }

        CandidateProfile updated = candidateProfileRepository.save(existing);

        return candidateProfileMapper.toCandidateProfileDTO(updated);
    }


    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Delete CandidateProfile");
    }

    @Transactional(readOnly = true)
    @Override
    public CandidateProfileDTO getById(Integer id) {
        log.info("Find CandidateProfile by id: {}", id);

        return candidateProfileMapper.toCandidateProfileDTO(
                candidateProfileRepository.findById(id)
                        .orElseThrow(() -> {
                            String message = "Find CandidateProfileDTO with id " + id + " not found";

                            log.error(message);

                            return new RuntimeException(message);
                        }));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CandidateProfileDTO> filter(CandidateProfileFilter candidateProfileFilter, int page, int size) {
        log.info("Filter CandidateProfile");

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<CandidateProfile> spec = CandidateProfileSpecification.filter(candidateProfileFilter);

        Page<CandidateProfile> pageResult = candidateProfileRepository.findAll(spec, pageable);

        return candidateProfileMapper.convertPageToList(pageResult);
    }

    @Transactional(readOnly = true)
    @Override
    public CandidateProfile getEntityById(Integer id) {
        log.info("Find Entity CandidateProfile by id: {}", id);

        return candidateProfileRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Find CandidateProfile with id " + id + " not found";

                    log.error(message);

                    return new RuntimeException(message);
                });
    }

    private Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();

        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
