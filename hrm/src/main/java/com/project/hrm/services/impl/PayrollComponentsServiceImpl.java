package com.project.hrm.services.impl;

import com.project.hrm.dto.payrollComponentsDTO.*;
import com.project.hrm.dto.regulationsDTO.RegulationsFilter;
import com.project.hrm.entities.PayrollComponents;
import com.project.hrm.entities.Payrolls;
import com.project.hrm.entities.Regulations;
import com.project.hrm.mapper.PayrollComponentMapper;
import com.project.hrm.mapper.RegulationsMapper;
import com.project.hrm.repositories.PayrollComponentsRepository;
import com.project.hrm.services.PayrollComponentsService;
import com.project.hrm.services.RegulationsService;
import com.project.hrm.specifications.PayrollComponentsSpecifications;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PayrollComponentsServiceImpl implements PayrollComponentsService {
    private final PayrollComponentsRepository payrollComponentsRepository;
    private final PayrollComponentMapper payrollComponentMapper;
    private final RegulationsService regulationsService;
    private final RegulationsMapper regulationsMapper;

    /**
     * Creates a new payroll component from the given DTO.
     *
     * @param payrollComponentsCreateDTO the creation data
     * @return the created {@link PayrollComponentsDTO}
     */
    @Transactional
    @Override
    public PayrollComponentsDTO create(PayrollComponentsCreateDTO payrollComponentsCreateDTO) {
        log.info("Create PayrollComponentsDTO");

        PayrollComponents payrollComponents = payrollComponentMapper.toPayrollComponentsFromCreateDTO(payrollComponentsCreateDTO);
        payrollComponents.setId(IdGenerator.getGenerationId());

        payrollComponents.setRegulation(payrollComponentsCreateDTO.getRegulationId() != null
                ? regulationsService.getEntityById(payrollComponentsCreateDTO.getRegulationId())
                : null);
        return payrollComponentMapper.toPayrollComponentsDTO(payrollComponentsRepository.save(payrollComponents));
    }

    /**
     * Updates an existing payroll component with new data.
     *
     * @param payrollComponentsUpdateDTO the update data
     * @return the updated {@link PayrollComponentsDTO}
     * @throws EntityNotFoundException if the component does not exist
     */
    @Transactional
    @Override
    public PayrollComponentsDTO update(PayrollComponentsUpdateDTO payrollComponentsUpdateDTO) {
        log.info("Update PayrollComponentsDTO");

        PayrollComponents payrollComponents = getEntityById(payrollComponentsUpdateDTO.getId());
        if(payrollComponentsUpdateDTO.getName() != null){
            payrollComponents.setName(payrollComponentsUpdateDTO.getName());
        }
        if (payrollComponentsUpdateDTO.getAmount() != null){
            payrollComponents.setAmount(payrollComponentsUpdateDTO.getAmount());
        }
        if (payrollComponentsUpdateDTO.getPercentage() != null){
            payrollComponents.setPercentage(payrollComponentsUpdateDTO.getPercentage());
        }
        if (payrollComponentsUpdateDTO.getRegulationId() != null){
            payrollComponents.setRegulation(regulationsService.getEntityById(payrollComponentsUpdateDTO.getRegulationId()));
        }
        if (payrollComponentsUpdateDTO.getType() != null){
            payrollComponents.setType(payrollComponentsUpdateDTO.getType());
        }

        PayrollComponents updatedPayrollComponents = payrollComponentsRepository.save(payrollComponents);
        return payrollComponentMapper.toPayrollComponentsDTO(updatedPayrollComponents);
    }

    /**
     * Deletes a payroll component by its ID.
     *
     * @param Id the component ID
     * @throws EntityNotFoundException if not found
     */
    @Transactional
    @Override
    public void delete(Integer Id) {
        log.info("Delete PayrollComponentsDTO");
        if (payrollComponentsRepository.existsById(Id)) {
            payrollComponentsRepository.deleteById(Id);
        }else {
            throw new EntityNotFoundException("PayrollComponents not found with id: " + Id);
        }
    }

    /**
     * Checks whether a payroll component exists by ID.
     *
     * @param Id the ID to check
     * @return true if exists, false otherwise
     */
    @Transactional
    @Override
    public Boolean checkExistence(Integer Id) {
        log.info("Check existence id: {}", Id);
        return payrollComponentsRepository.existsById(Id);
    }

    /**
     * Retrieves a payroll component DTO by ID.
     *
     * @param id the ID of the component
     * @return the {@link PayrollComponentsDTO}
     * @throws EntityNotFoundException if not found
     */
    @Transactional(readOnly = true)
    @Override
    public PayrollComponentsDTO getById(Integer id) {
        log.info("Get PayrollComponentsDTO by id: {}",id);
        return payrollComponentMapper.toPayrollComponentsDTO(getEntityById(id));
    }

    /**
     * Retrieves a payroll component entity by ID.
     *
     * @param id the ID of the component
     * @return the {@link PayrollComponents} entity
     * @throws EntityNotFoundException if not found
     */
    @Transactional(readOnly = true)
    @Override
    public PayrollComponents getEntityById(Integer id) {
        log.info("Get Payroll Components Entity by id: {}", id);
        return payrollComponentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payroll Components Entity with id: " + id + " not found"));
    }

    /**
     * Filters payroll components based on a dynamic set of conditions.
     *
     * @param payrollComponentFilter the filter criteria
     * @param page                   page number
     * @param size                   records per page
     * @return a list of matching {@link PayrollComponentsDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public List<PayrollComponentsDTO> filter(PayrollComponentsFilter payrollComponentFilter, int page, int size) {
        log.info("Filter PayrollComponents");

        Specification<PayrollComponents> payrollComponentsSpecification = PayrollComponentsSpecifications.filter(payrollComponentFilter);

        Pageable pageable = PageRequest.of(page, size);
        return payrollComponentMapper.convertPageEntityToPageDTO(payrollComponentsRepository.findAll(payrollComponentsSpecification, pageable));
    }

    /**
     * Filters payroll components with additional numeric range filtering.
     *
     * @param payrollComponentsFilterWithRange range and type filters
     * @param page                             page number
     * @param size                             records per page
     * @return a list of matching {@link PayrollComponentsDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public List<PayrollComponentsDTO> filterWithRange(PayrollComponentsFilterWithRange payrollComponentsFilterWithRange, int page, int size) {
        log.info("Filter with range PayrollComponents");

        Pageable pageable = PageRequest.of(page, size);

        Specification<PayrollComponents> payrollComponentsSpecification = PayrollComponentsSpecifications.filterWithRange(payrollComponentsFilterWithRange);

        return payrollComponentMapper.convertPageEntityToPageDTO(payrollComponentsRepository.findAll(payrollComponentsSpecification, pageable));
    }

    @Override
    public List<PayrollComponentsDTO> createComponents(Payrolls payrolls) {
        log.info("Create Payroll Components With Regulations");
        RegulationsFilter regulationsFilter = new RegulationsFilter();

        List<Regulations> regulations = regulationsService.filter(regulationsFilter, Integer.MAX_VALUE, Integer.MAX_VALUE)
                .stream()
                .map(regulationsMapper::toRegulations)
                .toList();
        List<PayrollComponents> components = new ArrayList<>();

        for (Regulations regulation : regulations) {
            PayrollComponents pc = getPayrollComponents(payrolls, regulation);
            components.add(pc);
        }

        return payrollComponentsRepository.saveAll(components)
                .stream()
                .map(payrollComponentMapper::toPayrollComponentsDTO)
                .collect(Collectors.toList());
    }

    private static PayrollComponents getPayrollComponents(Payrolls payrolls, Regulations regulation) {
        PayrollComponents pc = new PayrollComponents();
        pc.setPayroll(payrolls);
        pc.setRegulation(regulation);
        pc.setType(regulation.getType());
        if(regulation.getPercentage() != null){
            pc.setIsPercentage(Boolean.TRUE);
        }else {
            pc.setIsPercentage(Boolean.FALSE);
        }
        pc.setPercentage(regulation.getPercentage() != null ? regulation.getPercentage() : null);
        pc.setAmount(regulation.getAmount() != null ? regulation.getAmount() : null);
        return pc;
    }
}
