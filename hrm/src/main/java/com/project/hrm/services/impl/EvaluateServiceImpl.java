package com.project.hrm.services.impl;

import com.project.hrm.dto.evaluateDTO.EvaluateCreateDTO;
import com.project.hrm.dto.evaluateDTO.EvaluateDTO;
import com.project.hrm.dto.evaluateDTO.EvaluateFilter;
import com.project.hrm.dto.evaluateDTO.EvaluateUpdateDTO;
import com.project.hrm.entities.Account;
import com.project.hrm.entities.CandidateProfile;
import com.project.hrm.entities.Evaluate;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.EvaluateMapper;
import com.project.hrm.repositories.EvaluateRepository;
import com.project.hrm.services.AccountService;
import com.project.hrm.services.CandidateProfileService;
import com.project.hrm.services.EvaluateService;
import com.project.hrm.specifications.EvaluateSpecification;
import com.project.hrm.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class EvaluateServiceImpl implements EvaluateService {
    private final EvaluateRepository evaluateRepository;

    private final CandidateProfileService candidateProfileService;
    private final AccountService accountService;

    private final EvaluateMapper evaluateMapper;

    /**
     * Retrieves a paginated list of EvaluateDTOs based on filter conditions.
     *
     * @param evaluateFilter the filter conditions to apply.
     * @param page           the current page number (0-based).
     * @param size           the number of records per page.
     * @return a list of filtered {@link EvaluateDTO}.
     */
    @Transactional(readOnly = true)
    @Override
    public List<EvaluateDTO> filter(EvaluateFilter evaluateFilter, int page, int size) {
        log.info("Filter Evaluate");

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<Evaluate> spec = EvaluateSpecification.filter(evaluateFilter);
        Page<Evaluate> pageResult = evaluateRepository.findAll(spec, pageable);

        return evaluateMapper.convertPageToList(pageResult);
    }

    /**
     * Retrieves a specific EvaluateDTO by its ID.
     *
     * @param id the ID of the Evaluate entity to retrieve.
     * @return the corresponding {@link EvaluateDTO}.
     */
    @Transactional(readOnly = true)
    @Override
    public EvaluateDTO getById(Integer id) {
        log.info("Find Evaluate by id: {}", id);

        return evaluateMapper.toEvaluateDTO(evaluateRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.EVALUATE_NOT_FOUND)));
    }

    /**
     * Creates a new Evaluate entity from the provided DTO.
     *
     * @param evaluateCreateDTO the DTO containing the information to create a new Evaluate.
     * @return the created {@link EvaluateDTO} after being saved.
     */
    @Transactional
    @Override
    public EvaluateDTO create(EvaluateCreateDTO evaluateCreateDTO) {
        log.info("Create Evaluate");

        CandidateProfile candidateProfile = candidateProfileService.getEntityById(evaluateCreateDTO.getCandidateProfileId());

        Evaluate evaluate = evaluateMapper.conventCreateToEntity(evaluateCreateDTO, accountService.getPrincipal(), candidateProfile);
        evaluate.setId(IdGenerator.getGenerationId());

        return evaluateMapper.toEvaluateDTO(evaluateRepository.save(evaluate));
    }

    /**
     * Updates an existing Evaluate entity based on the non-null fields in the provided DTO.
     *
     * @param evaluateUpdateDTO the DTO containing updated values.
     * @return the updated {@link EvaluateDTO} after persistence.
     */
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

        if (evaluateUpdateDTO.getCandidateId() != null) {
            CandidateProfile candidate = candidateProfileService.getEntityById(evaluateUpdateDTO.getCandidateId());
            evaluate.setCandidateProfile(candidate);
        }

        if (evaluateUpdateDTO.getEvaluate() != null) {
            evaluate.setEvaluate(evaluateUpdateDTO.getEvaluate());
        }

        return evaluateMapper.toEvaluateDTO(evaluateRepository.save(evaluate));
    }

    /**
     * Deletes an existing Evaluate entity by its ID.
     *
     * @param evaluateId the ID of the Evaluate to delete.
     */
    @Transactional
    @Override
    public void delete(Integer evaluateId) {
        log.info("Delete Evaluate");

        Evaluate evaluate = evaluateRepository.findById(evaluateId)
                .orElseThrow();

        evaluateRepository.delete(evaluate);
    }
}
