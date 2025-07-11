package com.project.hrm.payrollManager.services;

import com.project.hrm.payrollManager.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.payrollManager.dto.payPeriodsDTO.PayPeriodsDTO;
import com.project.hrm.payrollManager.dto.payPeriodsDTO.PayPeriodsFilter;
import com.project.hrm.payrollManager.dto.payPeriodsDTO.PayPeriodsUpdateDTO;
import com.project.hrm.payrollManager.entities.PayPeriods;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface PayPeriodsService {
    PayPeriodsDTO create(PayPeriodsCreateDTO payPeriodsCreateDTO);

    PayPeriodsDTO update(PayPeriodsUpdateDTO payPeriodsUpdateDTO);

    void delete(Integer Id);

    Boolean checkExistence(Integer Id);

    PayPeriodsDTO getById(Integer id);

    PayPeriods getEntityById(Integer id);

    List<PayPeriodsDTO> filter(PayPeriodsFilter payPeriodsFilter, int page, int size);

    PayPeriods getPayPeriodsByDate(LocalDateTime startDate, LocalDateTime endDate);

    PayPeriods getOrCreatePayPeriod(LocalDateTime startDate, LocalDateTime endDate);
}
