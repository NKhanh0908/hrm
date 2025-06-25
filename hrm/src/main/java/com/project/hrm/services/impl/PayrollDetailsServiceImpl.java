package com.project.hrm.services.impl;

import com.project.hrm.dto.payrollDetailsDTO.*;
import com.project.hrm.entities.PayrollDetails;
import com.project.hrm.mapper.PayrollDetailsMapper;
import com.project.hrm.repositories.PayrollDetailsRepository;
import com.project.hrm.services.PayrollComponentsService;
import com.project.hrm.services.PayrollDetailsService;
import com.project.hrm.services.PayrollsService;
import com.project.hrm.specifications.PayrollDetailsSpecifications;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PayrollDetailsServiceImpl implements PayrollDetailsService {
    private final PayrollDetailsRepository payrollDetailsRepository;
    private final PayrollDetailsMapper payrollDetailsMapper;
    private final PayrollsService payrollsService;
    private final PayrollComponentsService payrollComponentsService;

    /**
     * Creates a new {@link PayrollDetails} entity from the provided DTO.
     *
     * @param payrollDetailsCreateDTO the creation input
     * @return created {@link PayrollDetailsDTO}
     */
    @Transactional
    @Override
    public PayrollDetailsDTO create(PayrollDetailsCreateDTO payrollDetailsCreateDTO) {
        log.info("Create payroll details");
        PayrollDetails payrollDetails = payrollDetailsMapper.toPayrollDetailsFromCreateDTO(payrollDetailsCreateDTO);
        payrollDetails.setId(IdGenerator.getGenerationId());
        payrollDetails.setPayroll(payrollsService.getEntityById(payrollDetailsCreateDTO.getPayrollId()));
        payrollDetails.setPayrollComponent(payrollComponentsService.getEntityById(payrollDetailsCreateDTO.getPayrollComponentId()));
        return payrollDetailsMapper.toDTO(payrollDetailsRepository.save(payrollDetails));
    }

    /**
     * Updates an existing payroll detail.
     *
     * @param payrollDetailsUpdateDTO the updated data
     * @return the updated {@link PayrollDetailsDTO}
     * @throws EntityNotFoundException if not found
     */
    @Transactional
    @Override
    public PayrollDetailsDTO update(PayrollDetailsUpdateDTO payrollDetailsUpdateDTO) {
        log.info("Update payroll details");
        PayrollDetails payrollDetails = getEntityById(payrollDetailsUpdateDTO.getId());

        if (payrollDetailsUpdateDTO.getPayrollId() != null && payrollsService.checkExistence(payrollDetailsUpdateDTO.getPayrollId())) {
            payrollDetails.setId(payrollDetailsUpdateDTO.getId());
        }

        if (payrollDetailsUpdateDTO.getPayrollComponentId() != null && payrollComponentsService.checkExistence(payrollDetailsUpdateDTO.getPayrollComponentId())) {
            payrollDetails.setId(payrollDetailsUpdateDTO.getId());
        }

        if (payrollDetailsUpdateDTO.getAmount() != null && payrollDetailsUpdateDTO.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            payrollDetails.setAmount(payrollDetailsUpdateDTO.getAmount());
        }

        if (payrollDetailsUpdateDTO.getIsPercentage() != null) {
            payrollDetails.setIsPercentage(payrollDetailsUpdateDTO.getIsPercentage());
        }

        if (payrollDetailsUpdateDTO.getPercentage() != null && payrollDetailsUpdateDTO.getPercentage() > 0) {
            payrollDetails.setPercentage(payrollDetailsUpdateDTO.getPercentage());
        }

        return payrollDetailsMapper.toDTO(payrollDetailsRepository.save(payrollDetails));
    }

    /**
     * Deletes a payroll detail by ID.
     *
     * @param Id the ID of the detail
     * @throws EntityNotFoundException if not found
     */
    @Transactional
    @Override
    public void delete(Integer Id) {
        log.info("Delete payroll details by id: {}", Id);
        if(checkExistence(Id)){
            payrollDetailsRepository.deleteById(Id);
        }else {
            throw new EntityNotFoundException("Payroll details not found with id: " + Id);
        }
    }

    /**
     * Checks if a payroll detail exists by ID.
     *
     * @param Id the ID to check
     * @return true if exists, false otherwise
     */

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistence(Integer Id) {
        log.info("Checking existence if PayrollDetails with Id: {} ", Id);
        return payrollDetailsRepository.existsById(Id);
    }

    /**
     * Retrieves a payroll detail by ID and returns as DTO.
     *
     * @param id the ID of the detail
     * @return the {@link PayrollDetailsDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public PayrollDetailsDTO getById(Integer id) {
        log.info("Get PayrollDetails by id: {}", id);
        return payrollDetailsMapper.toDTO(getEntityById(id));
    }

    /**
     * Retrieves a payroll detail entity by ID.
     *
     * @param id the ID of the detail
     * @return the {@link PayrollDetails} entity
     * @throws EntityNotFoundException if not found
     */
    @Transactional(readOnly = true)
    @Override
    public PayrollDetails getEntityById(Integer id) {
        log.info("Get payroll details by id: {}", id);
        return payrollDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payroll details not found with id: " + id));
    }

    /**
     * Filters payroll details based on specified conditions.
     *
     * @param payrollComponentFilter filtering conditions
     * @param page                   page number
     * @param size                   records per page
     * @return a list of {@link PayrollDetailsDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public List<PayrollDetailsDTO> filter(PayrollDetailsFilter payrollComponentFilter, int page, int size) {
        log.info("Filter payroll details by filter: {}", payrollComponentFilter);

        Pageable pageable = PageRequest.of(page, size);

        Specification<PayrollDetails> specification = PayrollDetailsSpecifications.filter(payrollComponentFilter);

        return payrollDetailsRepository.findAll(specification, pageable)
                .getContent()
                .stream()
                .map(payrollDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filters payroll details with numeric range conditions.
     *
     * @param payrollDetailsFilterWithRange the filtering criteria including ranges
     * @param page                          page number
     * @param size                          page size
     * @return filtered list of {@link PayrollDetailsDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public List<PayrollDetailsDTO> filterWithRange(PayrollDetailsFilterWithRange payrollDetailsFilterWithRange, int page, int size) {
        log.info("Filter payroll details with range");
        Pageable pageable = PageRequest.of(page, size);

        Specification<PayrollDetails> specification = PayrollDetailsSpecifications.filterWithRange(payrollDetailsFilterWithRange);

        return payrollDetailsRepository.findAll(specification, pageable)
                .getContent()
                .stream()
                .map(payrollDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }
}
