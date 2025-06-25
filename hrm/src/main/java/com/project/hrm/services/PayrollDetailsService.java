package com.project.hrm.services;

import com.project.hrm.dto.payrollDetailsDTO.*;
import com.project.hrm.entities.PayrollDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface PayrollDetailsService {
    PayrollDetailsDTO create(PayrollDetailsCreateDTO payrollDetailsCreateDTO);

    PayrollDetailsDTO update(PayrollDetailsUpdateDTO payrollDetailsUpdateDTO);

    void delete(Integer Id);

    Boolean checkExistence(Integer Id);

    PayrollDetailsDTO getById(Integer id);

    PayrollDetails getEntityById(Integer id);

    List<PayrollDetailsDTO> filter(PayrollDetailsFilter payrollComponentFilter, int page, int size);

    List<PayrollDetailsDTO> filterWithRange(PayrollDetailsFilterWithRange payrollDetailsFilterWithRange, int page, int size);
}
