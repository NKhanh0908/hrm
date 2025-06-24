package com.project.hrm.services;

import com.project.hrm.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsFilter;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsUpdateDTO;
import com.project.hrm.entities.PayPeriods;
import org.springframework.stereotype.Service;

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
}
