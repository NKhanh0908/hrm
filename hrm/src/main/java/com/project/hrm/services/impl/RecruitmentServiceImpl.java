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
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class RecruitmentServiceImpl implements RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentMapper recruitmentMapper;
    private final RecruitmentRequirementService recruitmentRequirementService;

    @Transactional(readOnly = true)
    @Override
    public Page<RecruitmentDTO> filter(RecruitmentFilter recruitmentFilter, int page, int size) {
        Specification<Recruitment> recruitmentSpecification
                = RecruitmentSpecification.filter(recruitmentFilter);

        Pageable pageable = PageRequest.of(page, size);

        Page<Recruitment> recruitmentPage
                = recruitmentRepository.filter(recruitmentSpecification, pageable);

        return recruitmentMapper.convertPageEntityToPageDTO(recruitmentPage);
    }

    @Transactional(readOnly = true)
    @Override
    public Recruitment getEntityById(Integer id) {
        return recruitmentRepository.findById(id)
                .orElseThrow(
                        () -> {
                            String message = "Find Recruitment with id " + id + " not found";

                            log.error(message);

                            return new RuntimeException(message);
                        }
                );
    }

    @Transactional(readOnly = true)
    @Override
    public RecruitmentDTO getDTOById(Integer id) {
        return recruitmentMapper.toDTO(
                recruitmentRepository.findById(id).orElseThrow(
                () -> {
                    String message = "Find RecruitmentDTO with id " + id + " not found";

                    log.error(message);

                    return new RuntimeException(message);
                }
        ));
    }

    @Override
    public Boolean checkExists(Integer recruitmentId) {
        return null;
    }

    @Transactional
    @Override
    public RecruitmentDTO create(RecruitmentCreateDTO recruitmentCreateDTO) {
        RecruitmentRequirements recruitmentRequirements = recruitmentRequirementService.getEntityById(recruitmentCreateDTO.getRecruitmentRequirementId());

        Recruitment recruitment = recruitmentMapper.convertCreateToEntity(recruitmentCreateDTO, recruitmentRequirements);

        recruitment.setId(getGenerationId());
        recruitment.setCreateAt(LocalDateTime.now());

        return recruitmentMapper.toDTO(recruitmentRepository.save(recruitment));
    }

    @Transactional
    @Override
    public RecruitmentDTO update(RecruitmentUpdateDTO recruitmentUpdateDTO) {
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

    @Transactional
    @Override
    public void delete(Integer recruitmentId) {

        Recruitment recruitment = getEntityById(recruitmentId);

        recruitmentRepository.delete(recruitment);

    }

    private Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();

        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
