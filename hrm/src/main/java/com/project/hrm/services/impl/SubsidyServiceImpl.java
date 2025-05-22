package com.project.hrm.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import org.springframework.stereotype.Service;

import com.project.hrm.dto.salaryDTO.SubsidyDTO;
import com.project.hrm.dto.salaryDTO.SubsidyCreateDTO;
import com.project.hrm.dto.salaryDTO.SubsidyUpdateDTO;
import com.project.hrm.entities.Salary;
import com.project.hrm.entities.Subsidy;
import com.project.hrm.mapper.SubsidyMapper;
import com.project.hrm.repositories.SubsidyRepository;
import com.project.hrm.services.SalaryService;
import com.project.hrm.services.SubsidyService;
import com.project.hrm.utils.IdGenerator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class SubsidyServiceImpl implements SubsidyService {

    private final SubsidyRepository subsidyRepository;
    private final SubsidyMapper subsidyMapper;
    private final SalaryService salaryService;

    /**
     * Retrieves all {@link Subsidy} entities.
     *
     * @return a list of {@link SubsidyDTO}
     */
    @Override
    public List<SubsidyDTO> getAll() {
        log.info("Fetching all subsidies");

        List<Subsidy> list = subsidyRepository.findAll();

        return list.stream()
                .map(subsidyMapper::toSubsidyDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a {@link Subsidy} by its ID.
     *
     * @param id the ID of the subsidy
     * @return the corresponding {@link SubsidyDTO}
     * @throws CustomException if the subsidy is not found
     */
    @Override
    public SubsidyDTO getById(Integer id) {
        log.info("Fetching subsidy by ID: {}", id);
        return subsidyMapper.toSubsidyDTO(
                subsidyRepository.findById(id)
                        .orElseThrow(() -> new CustomException(Error.SUBSIDY_NOT_FOUND)));
    }

    /**
     * Checks if a subsidy with the specified ID exists.
     *
     * @param subsidyId the ID to check
     * @return {@code true} if exists, otherwise {@code false}
     */
    @Override
    public Boolean checkExists(Integer subsidyId) {
        log.info("Checking existence of subsidy ID: {}", subsidyId);
        return subsidyRepository.existsById(subsidyId);
    }

    /**
     * Creates a new {@link Subsidy} from the provided {@link SubsidyCreateDTO}.
     *
     * @param subsidyCreateDTO the DTO containing data required to create a subsidy
     * @return the created {@link SubsidyDTO}
     */
    @Transactional
    @Override
    public SubsidyDTO create(SubsidyCreateDTO subsidyCreateDTO) {
        log.info("Creating subsidy");

        Salary salary = salaryService.getEntityById(subsidyCreateDTO.getSalaryId());
        Subsidy subsidy = subsidyMapper.toSubsidyFromCreateDTO(subsidyCreateDTO, salary);
        subsidy.setId(IdGenerator.getGenerationId());

        Subsidy saved = subsidyRepository.save(subsidy);
        log.info("Subsidy created with ID: {}", saved.getId());

        return subsidyMapper.toSubsidyDTO(saved);
    }

    /**
     * Updates an existing {@link Subsidy} entity with values from
     * {@link SubsidyUpdateDTO}.
     *
     * @param subsidyUpdateDTO the DTO containing updated values
     * @return the updated {@link SubsidyDTO}
     */
    @Transactional
    @Override
    public SubsidyDTO update(SubsidyUpdateDTO subsidyUpdateDTO) {
        log.info("Updating subsidy with ID: {}", subsidyUpdateDTO.getId());

        Subsidy subsidy = subsidyMapper.toSubsidy(getById(subsidyUpdateDTO.getId()));

        if (subsidyUpdateDTO.getTypeSubsidy() != null) {
            subsidy.setTypeSubsidy(subsidyUpdateDTO.getTypeSubsidy());
        }

        if (subsidyUpdateDTO.getAmount() != null) {
            subsidy.setAmount(subsidyUpdateDTO.getAmount());
        }

        if (subsidyUpdateDTO.getSalaryId() != null) {
            subsidy.setSalary(salaryService.getEntityById(subsidyUpdateDTO.getSalaryId()));
        }

        Subsidy saved = subsidyRepository.save(subsidy);
        log.info("Subsidy updated with ID: {}", saved.getId());

        return subsidyMapper.toSubsidyDTO(saved);
    }

    /**
     * Deletes a {@link Subsidy} by its ID.
     *
     * @param subsidyId the ID of the subsidy to delete
     * @throws RuntimeException if the subsidy is not found
     */
    @Transactional
    @Override
    public void delete(Integer subsidyId) {
        log.info("Deleting subsidy with ID: {}", subsidyId);

        if (checkExists(subsidyId)) {
            subsidyRepository.deleteById(subsidyId);
            log.info("Subsidy deleted with ID: {}", subsidyId);
        } else {
            log.warn("Subsidy with ID {} not found", subsidyId);
            throw new CustomException(Error.SUBSIDY_NOT_FOUND);
        }
    }

}
