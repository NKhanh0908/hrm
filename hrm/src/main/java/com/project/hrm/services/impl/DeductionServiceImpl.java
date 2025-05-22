package com.project.hrm.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import org.springframework.stereotype.Service;
import com.project.hrm.dto.salaryDTO.DeductionCreateDTO;
import com.project.hrm.dto.salaryDTO.DeductionDTO;
import com.project.hrm.dto.salaryDTO.DeductionUpdateDTO;
import com.project.hrm.entities.Deduction;
import com.project.hrm.entities.Salary;
import com.project.hrm.mapper.DeductionMapper;
import com.project.hrm.repositories.DeductionRepository;
import com.project.hrm.services.DeductionService;
import com.project.hrm.services.SalaryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class DeductionServiceImpl implements DeductionService {
    private final DeductionRepository deductionRepository;

    private final DeductionMapper deductionMapper;

    private final SalaryService salaryService;

    /**
     * Retrieves all {@link Deduction} entities.
     *
     * @return a list of {@link DeductionDTO}
     */
    @Override
    public List<DeductionDTO> getAll() {
        log.info("Fetching all deductions");

        List<Deduction> list = deductionRepository.findAll();

        return list.stream()
                .map(deductionMapper::toDeductionDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a {@link Deduction} entity by its ID.
     *
     * @param id the ID of the deduction
     * @return the corresponding {@link DeductionDTO}
     * @throws CustomException if the deduction is not found
     */
    @Override
    public DeductionDTO getById(Integer id) {
        log.info("Fetching deduction by id: {}", id);

        return deductionMapper.toDeductionDTO(
                deductionRepository.findById(id)
                        .orElseThrow(() -> new CustomException(Error.DEDUCTION_NOT_FOUND))
        );
    }

    /**
     * Checks if a deduction with the specified ID exists.
     *
     * @param deductionId the ID to check
     * @return {@code true} if it exists, otherwise {@code false}
     */
    @Override
    public Boolean checkExists(Integer deductionId) {
        log.info("Checking existence of deduction id: {}", deductionId);

        return deductionRepository.existsById(deductionId);
    }

    /**
     * Creates a new {@link Deduction} entity from the provided {@link DeductionCreateDTO}.
     *
     * @param deductionCreateDTO the DTO containing data required to create a deduction
     * @return the created {@link DeductionDTO} after being persisted
     */
    @Transactional
    @Override
    public DeductionDTO create(DeductionCreateDTO deductionCreateDTO) {
        log.info("Creating deduction");

        Salary salary = salaryService.getEntityById(deductionCreateDTO.getSalaryId());
        Deduction deduction = deductionMapper.toDeductionFromCreateDTO(deductionCreateDTO, salary);

        Deduction saved = deductionRepository.save(deduction);
        log.info("Deduction created with ID: {}", saved.getId());

        return deductionMapper.toDeductionDTO(saved);
    }

    /**
     * Updates an existing {@link Deduction} entity with data from {@link DeductionUpdateDTO}.
     *
     * @param deductionUpdateDTO the DTO containing fields to update
     * @return the updated {@link DeductionDTO}
     */
    @Transactional
    @Override
    public DeductionDTO update(DeductionUpdateDTO deductionUpdateDTO) {
        log.info("Updating deduction with ID: {}", deductionUpdateDTO.getId());

        Deduction deduction = deductionMapper.toDeduction(getById(deductionUpdateDTO.getId()));

        if (deductionUpdateDTO.getTypeDeduction() != null) {
            deduction.setTypeDeduction(deductionUpdateDTO.getTypeDeduction());
        }

        if (deductionUpdateDTO.getAmount() != null) {
            deduction.setAmount(deductionUpdateDTO.getAmount());
        }

        if (deductionUpdateDTO.getSalaryId() != null) {
            deduction.setSalary(salaryService.getEntityById(deductionUpdateDTO.getSalaryId()));
        }

        Deduction saved = deductionRepository.save(deduction);
        log.info("Deduction updated with ID: {}", saved.getId());

        return deductionMapper.toDeductionDTO(saved);
    }

    /**
     * Deletes the {@link Deduction} entity with the given ID.
     *
     * @param deductionId the ID of the deduction to delete
     * @throws RuntimeException if the deduction is not found
     */
    @Transactional
    @Override
    public void delete(Integer deductionId) {
        log.info("Deleting deduction with ID: {}", deductionId);

        if (checkExists(deductionId)) {
            deductionRepository.deleteById(deductionId);
            log.info("Deduction with ID: {} deleted", deductionId);
        } else {
            log.warn("Deduction with ID: {} not found", deductionId);
            throw new CustomException(Error.DEDUCTION_NOT_FOUND);
        }
    }
}
