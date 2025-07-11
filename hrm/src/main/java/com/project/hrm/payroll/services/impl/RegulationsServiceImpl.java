package com.project.hrm.payroll.services.impl;

import com.project.hrm.payroll.dto.regulationsDTO.RegulationsCreateDTO;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsDTO;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsFilter;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsUpdateDTO;
import com.project.hrm.payroll.entities.Regulations;
import com.project.hrm.payroll.enums.PayrollComponentType;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.payroll.mapper.RegulationsMapper;
import com.project.hrm.payroll.repositories.RegulationsRepository;
import com.project.hrm.payroll.services.RegulationsService;
import com.project.hrm.payroll.specifications.RegulationsSpecification;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RegulationsServiceImpl implements RegulationsService {

    private final RegulationsRepository regulationsRepository;
    private final RegulationsMapper regulationsMapper;


    /**
     * Filters {@link Regulations} entities based on the given {@link RegulationsFilter} and pagination.
     *
     * @param filter the filter criteria
     * @param page   page number (zero-based)
     * @param size   number of items per page
     * @return a list of {@link RegulationsDTO} matching the filter
     */
    @Transactional(readOnly = true)
    @Override
    public List<RegulationsDTO> filter(RegulationsFilter filter, int page, int size) {
        log.info("filter regulations");

        Specification<Regulations> spec = RegulationsSpecification.filterRegulationsSpecification(filter);

        Pageable pageable = PageRequest.of(page, size);

        return regulationsMapper.convertPageEntityToPageDTO(regulationsRepository.findAll(spec, pageable));
    }

    /**
     * Retrieves a {@link RegulationsDTO} by its ID.
     *
     * @param id the ID of the regulation
     * @return the corresponding {@link RegulationsDTO}
     * @throws EntityNotFoundException if the regulation is not found
     */
    @Transactional(readOnly = true)
    @Override
    public RegulationsDTO getById(Integer id) {
        log.info("Get Regulations by id: {}",  id);
        return regulationsMapper.toRegulationsDTO(getEntityById(id));
    }

    /**
     * Retrieves a {@link Regulations} entity by its ID.
     *
     * @param id the ID of the regulation
     * @return the {@link Regulations} entity
     * @throws EntityNotFoundException if not found
     */
    @Transactional(readOnly = true)
    @Override
    public Regulations getEntityById(Integer id) {
        log.info("Get Regulations Entity by id: {}",  id);
        return regulationsRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.REGULATION_NOT_FOUND));
    }

    /**
     * Checks whether a {@link Regulations} exists with the given ID.
     *
     * @param regulationId the ID to check
     * @return true if exists, false otherwise
     */
    @Transactional(readOnly = true)
    @Override
    public Boolean checkExists(Integer regulationId) {
        log.info("Check existence of Regulations with id: {}", regulationId);
        return regulationsRepository.existsById(regulationId);
    }

    /**
     * Creates a new {@link Regulations} entity based on the given {@link RegulationsCreateDTO}.
     *
     * @param regulationsCreateDTO the data for creation
     * @return the created {@link RegulationsDTO}
     */
    @Transactional
    @Override
    public RegulationsDTO create(RegulationsCreateDTO regulationsCreateDTO) {
        log.info("Create Regulations DTO: {}", regulationsCreateDTO);

        Regulations regulations = regulationsMapper.toRegulationsFromCreateDTO(regulationsCreateDTO);
        regulations.setId(IdGenerator.getGenerationId());

        return regulationsMapper.toRegulationsDTO(regulationsRepository.save(regulations));
    }

    /**
     * Updates an existing {@link Regulations} entity using the given {@link RegulationsUpdateDTO}.
     *
     * @param regulationsUpdateDTO the update data
     * @return the updated {@link RegulationsDTO}
     * @throws EntityNotFoundException if the regulation does not exist
     */
    @Transactional
    @Override
    public RegulationsDTO update(RegulationsUpdateDTO regulationsUpdateDTO) {
        log.info("Update Regulations with ID: {}", regulationsUpdateDTO.getId());

        Regulations regulations = getEntityById(regulationsUpdateDTO.getId());

        if (regulationsUpdateDTO.getName() != null) {
            regulations.setName(regulationsUpdateDTO.getName());
        }
        if (regulationsUpdateDTO.getApplicableSalary() != null) {
            regulations.setApplicableSalary(regulationsUpdateDTO.getApplicableSalary());
        }
        if (regulationsUpdateDTO.getPercentage() != null) {
            regulations.setPercentage(regulationsUpdateDTO.getPercentage());
        }
        if (regulationsUpdateDTO.getAmount() != null) {
            regulations.setAmount(regulationsUpdateDTO.getAmount());
        }
        if (regulationsUpdateDTO.getEffectiveDate() != null) {
            regulations.setEffectiveDate(regulationsUpdateDTO.getEffectiveDate());
        }

        Regulations updated = regulationsRepository.save(regulations);
        return regulationsMapper.toRegulationsDTO(updated);
    }

    /**
     * Deletes a {@link Regulations} entity by its ID.
     *
     * @param regulationId the ID to delete
     * @throws EntityNotFoundException if not found
     */
    @Transactional
    @Override
    public void delete(Integer regulationId) {
        log.info("Delete Regulations with id: {}", regulationId);

        if (checkExists(regulationId)) {
            regulationsRepository.deleteById(regulationId);
        }else {
            throw new CustomException(Error.REGULATION_NOT_FOUND);
        }
    }

    @Override
    public Regulations getRegulationsByKey(String regulationsKey) {
        log.info("Get Regulations by regulationsKey: {}", regulationsKey);
        return regulationsRepository.findRegulationByKey(regulationsKey)
                .orElseThrow(() -> new CustomException(Error.REGULATION_NOT_FOUND));
    }

    @Override
    public List<Regulations> findRegulationByType(PayrollComponentType type) {
        log.info("Find Regulations by type: {}", type);
        return regulationsRepository.findByType(type);
    }

    @Override
    public List<Regulations> findAllById(Iterable<Integer> ids) {
        log.info("Find Regulations by ids: {}", ids);
        return regulationsRepository.findAllById(ids);
    }
}
