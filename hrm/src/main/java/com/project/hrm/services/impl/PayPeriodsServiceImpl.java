package com.project.hrm.services.impl;

import com.project.hrm.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsFilter;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsUpdateDTO;
import com.project.hrm.entities.PayPeriods;
import com.project.hrm.mapper.PayPeriodMapper;
import com.project.hrm.repositories.PayPeriodsRepository;
import com.project.hrm.services.PayPeriodsService;
import com.project.hrm.specifications.PayPeriodsSpecifications;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PayPeriodsServiceImpl implements PayPeriodsService {

    private final PayPeriodsRepository payPeriodsRepository;
    private final PayPeriodMapper payPeriodMapper;

    @Transactional
    @Override
    public PayPeriodsDTO create(PayPeriodsCreateDTO payPeriodsCreateDTO) {
        log.info("Create PayPeriods ");

        PayPeriods payPeriods = payPeriodMapper.toPayPeriodsFromCreateDTO(payPeriodsCreateDTO);
        payPeriods.setId(IdGenerator.getGenerationId());
        return payPeriodMapper.toPayPeriodDTO(payPeriodsRepository.save(payPeriods));
    }

    @Override
    @Transactional
    public PayPeriodsDTO update(PayPeriodsUpdateDTO payPeriodsUpdateDTO) {
        log.info("Update PayPeriods with id {} ", payPeriodsUpdateDTO.getId());

        PayPeriods payPeriods = getEntityById(payPeriodsUpdateDTO.getId());

        if (payPeriodsUpdateDTO.getPayPeriodCode() != null && !payPeriodsUpdateDTO.getPayPeriodCode().isEmpty()) {
            payPeriods.setPayPeriodCode(payPeriodsUpdateDTO.getPayPeriodCode());
        }

        if (payPeriodsUpdateDTO.getStartDate() != null) {
            payPeriods.setStartDate(payPeriodsUpdateDTO.getStartDate());
        }

        if (payPeriodsUpdateDTO.getEndDate() != null) {
            payPeriods.setEndDate(payPeriodsUpdateDTO.getEndDate());
        }

        if (payPeriodsUpdateDTO.getStatus() != null) {
            payPeriods.setStatus(payPeriodsUpdateDTO.getStatus());
        }

        return payPeriodMapper.toPayPeriodDTO(payPeriodsRepository.save(payPeriods));
    }



    @Transactional
    @Override
    public void delete(Integer Id) {
        log.info("Delete PayPeriodsDTO with Id: {}", Id);
        if (checkExistence(Id)){
            payPeriodsRepository.deleteById(Id);
        }else {
            throw new EntityNotFoundException("PayPeriods with Id: " + Id + " not found");
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistence(Integer Id) {
        log.info("Check existence of PayPeriods with Id: {}", Id);
        return payPeriodsRepository.existsById(Id);
    }


    @Transactional(readOnly = true)
    @Override
    public PayPeriodsDTO getById(Integer id) {
        log.info("Get PayPeriodsDTO by id: {}", id);
        return payPeriodMapper.toPayPeriodDTO(getEntityById(id));
    }


    @Transactional(readOnly = true)
    @Override
    public PayPeriods getEntityById(Integer id) {
        log.info("Get PayPeriods by id: {}", id);
        return payPeriodsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pay Periods not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PayPeriodsDTO> filter(PayPeriodsFilter payPeriodsFilter, int page, int size) {
        log.info("Filter PayPeriodsDTO");

        Specification<PayPeriods> spec = PayPeriodsSpecifications.filter(payPeriodsFilter);

        Pageable pageable = PageRequest.of(page, size);

        return payPeriodMapper.convertPageEntityToPageDTO(payPeriodsRepository.findAll(spec, pageable));
    }

    @Transactional(readOnly = true)
    @Override
    public PayPeriods getPayPeriodsByDate(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Check existence of PayPeriods with startDate: {}, endDate: {}", startDate, endDate);


        return payPeriodsRepository.getByStartDateLessThanEqualAndEndDateGreaterThanEqual(endDate,startDate);
    }
}
