package com.project.hrm.services.impl;

import com.project.hrm.dto.evaluateDTO.EvaluateCreateDTO;
import com.project.hrm.dto.evaluateDTO.EvaluateDTO;
import com.project.hrm.dto.evaluateDTO.EvaluateFilter;
import com.project.hrm.dto.evaluateDTO.EvaluateUpdateDTO;
import com.project.hrm.entities.CandidateProfile;
import com.project.hrm.entities.Evaluate;
import com.project.hrm.mapper.EvaluateMapper;
import com.project.hrm.repositories.EvaluateRepository;
import com.project.hrm.services.CandidateProfileService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.EvaluateService;
import com.project.hrm.specifications.EvaluateSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
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
public class EvaluateServiceImpl implements EvaluateService {
    private final EvaluateMapper evaluateMapper;
    private final EvaluateRepository evaluateRepository;
    private final EmployeeService employeeService;
    private final CandidateProfileService candidateProfileService;

    @Transactional(readOnly = true)
    @Override
    public List<EvaluateDTO> filter(EvaluateFilter evaluateFilter,int page,int size) {
        log.info("Filter Evaluate");

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Specification<Evaluate> spec = EvaluateSpecification.filter(evaluateFilter);

        Page<Evaluate> pageResult = evaluateRepository.findAll(spec, pageable);

        return evaluateMapper.convertPageToList(pageResult);

    }

    @Transactional(readOnly = true)
    @Override
    public EvaluateDTO getById(Integer id) {
        log.info("Find Evaluate by id: {}", id);

        return evaluateMapper.toEvaluateDTO(
                evaluateRepository.findById(id).orElseThrow()
        );
    }

    @Override
    public Boolean checkExists(Integer evaluateId) {
        return null;
    }

    @Transactional
    @Override
    public EvaluateDTO create(EvaluateCreateDTO evaluateCreateDTO) {
        log.info("Create Evaluate");

        Evaluate evaluate=evaluateMapper.conventCreateToEntity(evaluateCreateDTO);

        evaluate.setId(getGenerationId());

        CandidateProfile candidateProfile=candidateProfileService.getEntityById(evaluateCreateDTO.getCandidateProfileId());
        evaluate.setCandidateProfile(candidateProfile);
        //set employee

        return evaluateMapper.toEvaluateDTO(evaluateRepository.save(evaluate));
    }

    @Transactional
    @Override
    public EvaluateDTO update(EvaluateUpdateDTO evaluateUpdateDTO) {
        log.info("Update Evaluate");

        Evaluate evaluate = evaluateRepository.findById(evaluateUpdateDTO.getId())
                .orElseThrow();

        if (evaluateUpdateDTO.getFeedback() != null) {
            evaluate.setFeedback(evaluateUpdateDTO.getFeedback());
        }
        if (evaluateUpdateDTO.getFeedbackAt() != null) {
            evaluate.setFeedbackAt(evaluateUpdateDTO.getFeedbackAt());
        }
        if (evaluateUpdateDTO.getEvaluate() != null) {
            evaluate.setEvaluate(evaluateUpdateDTO.getEvaluate());
        }
        if (evaluateUpdateDTO.getCandidateId() != null) {
            CandidateProfile candidate = candidateProfileService.getEntityById(evaluateUpdateDTO.getCandidateId());
            evaluate.setCandidateProfile(candidate);
        }
        if (evaluateUpdateDTO.getCreateBy() != null) {

        }

        return evaluateMapper.toEvaluateDTO(evaluateRepository.save(evaluate));
    }


    @Override
    public void delete(Integer evaluateId) {

    }
    private Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();

        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
