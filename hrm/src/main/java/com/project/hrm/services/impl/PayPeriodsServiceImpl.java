package com.project.hrm.services.impl;

import com.project.hrm.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsFilter;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsUpdateDTO;
import com.project.hrm.entities.PayPeriods;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.PayPeriodMapper;
import com.project.hrm.repositories.PayPeriodsRepository;
import com.project.hrm.services.PayPeriodsService;
import com.project.hrm.specifications.PayPeriodsSpecifications;
import com.project.hrm.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        log.info("Create PayPeriods with code: {}", payPeriodsCreateDTO.getPayPeriodCode());

        PayPeriods payPeriods = payPeriodMapper.toPayPeriodsFromCreateDTO(payPeriodsCreateDTO);
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
    public void delete(Integer id) {
        log.info("Delete PayPeriodsDTO with id: {}", id);
        if (checkExistence(id)){
            payPeriodsRepository.deleteById(id);
        }else {
            throw new CustomException(Error.PAY_PERIOD_NOT_FOUND);
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistence(Integer id) {
        log.info("Check existence of PayPeriods with id: {}", id);
        return payPeriodsRepository.existsById(id);
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
                .orElseThrow(() -> new CustomException(Error.PAY_PERIOD_NOT_FOUND));
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
        log.info("Get PayPeriods with startDate: {}, endDate: {}", startDate, endDate);

        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            log.error("Invalid date range: startDate={}, endDate={}", startDate, endDate);
            throw new IllegalArgumentException("Invalid pay period date range: startDate must be before or equal to endDate");
        }

        return payPeriodsRepository.getByStartDateLessThanEqualAndEndDateGreaterThanEqual(startDate, endDate)
                .orElse(null);
    }
}
