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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CandidateProfileServiceImpl implements CandidateProfileService {
    private final CandidateProfileRepository candidateProfileRepository;
    private final CandidateProfileMapper candidateProfileMapper;
    @Override
    public CandidateProfileDTO create(CandidateProfileCreateDTO candidateProfileCreateDTO) {
        CandidateProfile candidateProfile=candidateProfileMapper.convertCreateToEntity(candidateProfileCreateDTO);

        candidateProfile.setId(getGenerationId());
        return candidateProfileMapper.toCandidateProfileDTO(
                candidateProfileRepository.save(candidateProfile)
        );
    }

    @Override
    public CandidateProfileDTO update(CandidateProfileUpdateDTO dto) {
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


    @Override
    public void delete(Integer id) {

    }

    @Override
    public CandidateProfileDTO getById(Integer id) {
        return candidateProfileMapper.toCandidateProfileDTO(
                candidateProfileRepository.findById(id)
                        .orElseThrow()
        );
    }

    @Override
    public List<CandidateProfileDTO> filter(CandidateProfileFilter candidateProfileFilter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending()); // sort mặc định theo id giảm dần
        Specification<CandidateProfile> spec = CandidateProfileSpecification.filter(candidateProfileFilter);

        Page<CandidateProfile> pageResult = candidateProfileRepository.findAll(spec, pageable);

        return pageResult.getContent().stream()
                .map(candidateProfileMapper::toCandidateProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CandidateProfile getEntityById(Integer id) {
        return candidateProfileRepository.findById(id).orElseThrow();
    }

    private Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();

        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
