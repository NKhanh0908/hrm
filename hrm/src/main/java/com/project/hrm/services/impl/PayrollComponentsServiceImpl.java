package com.project.hrm.services.impl;

import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsCreateDTO;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsDTO;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsFilter;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsUpdateDTO;
import com.project.hrm.entities.PayrollComponents;
import com.project.hrm.enums.PayrollComponentType;
import com.project.hrm.mapper.PayrollComponentMapper;
import com.project.hrm.repositories.PayrollComponentsRepository;
import com.project.hrm.repositories.RegulationsRepository;
import com.project.hrm.services.PayrollComponentsService;
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

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PayrollComponentsServiceImpl implements PayrollComponentsService {
    private final PayrollComponentsRepository payrollComponentsRepository;
    private final PayrollComponentMapper payrollComponentMapper;
    private final RegulationsRepository regulationsRepository;

    @Transactional
    @Override
    public PayrollComponentsDTO create(PayrollComponentsCreateDTO payrollComponentsCreateDTO) {
        log.info("Create PayrollComponentsDTO");

        PayrollComponents payrollComponents = payrollComponentMapper.toPayrollComponentsFromCreateDTO(payrollComponentsCreateDTO);
        payrollComponents.setId(IdGenerator.getGenerationId());
        return payrollComponentMapper.toPayrollComponentsDTO(payrollComponentsRepository.save(payrollComponents));
    }

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
            payrollComponents.setRegulation(regulationsRepository.findById(payrollComponentsUpdateDTO.getRegulationId())
                    .orElseThrow(() -> new EntityNotFoundException("Regulations not found with id: " + payrollComponentsUpdateDTO.getRegulationId())));
        }
        if (payrollComponentsUpdateDTO.getType() != null){
            payrollComponents.setType(payrollComponentsUpdateDTO.getType());
        }

        PayrollComponents updatedPayrollComponents = payrollComponentsRepository.save(payrollComponents);
        return payrollComponentMapper.toPayrollComponentsDTO(updatedPayrollComponents);
    }

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

    @Transactional
    @Override
    public Boolean checkExistence(Integer Id) {
        log.info("Check existence id: {}", Id);
        return payrollComponentsRepository.existsById(Id);
    }

    @Transactional(readOnly = true)
    @Override
    public PayrollComponentsDTO getById(Integer id) {
        log.info("Get PayrollComponentsDTO by id: {}",id);
        return payrollComponentMapper.toPayrollComponentsDTO(getEntityById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public PayrollComponents getEntityById(Integer id) {
        log.info("Get Payroll Components Entity by id: {}", id);
        return payrollComponentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payroll Components Entity with id: " + id + " not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PayrollComponentsDTO> filter(PayrollComponentsFilter payrollComponentFilter, int page, int size) {
        log.info("Filter PayrollComponents");

        Specification<PayrollComponents> payrollComponentsSpecification = PayrollComponentsSpecifications.filter(payrollComponentFilter);

        Pageable pageable = PageRequest.of(page, size);
        return payrollComponentMapper.convertPageEntityToPageDTO(payrollComponentsRepository.findAll(payrollComponentsSpecification, pageable));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PayrollComponentsDTO> filterWithRange(BigDecimal minAmount, BigDecimal maxAmount, Float minPercentage, Float maxPercentage, PayrollComponentType type, int page, int size) {
        log.info("Filter with range PayrollComponents");

        Pageable pageable = PageRequest.of(page, size);

        Specification<PayrollComponents> payrollComponentsSpecification = PayrollComponentsSpecifications.filterWithRange(minAmount, maxAmount, minPercentage, maxPercentage, type);

        return payrollComponentMapper.convertPageEntityToPageDTO(payrollComponentsRepository.findAll(payrollComponentsSpecification, pageable));
    }
}
