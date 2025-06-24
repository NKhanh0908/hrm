package com.project.hrm.services;

import com.project.hrm.dto.payrollDetailsDTO.PayrollDetailsCreateDTO;
import com.project.hrm.dto.payrollDetailsDTO.PayrollDetailsDTO;
import com.project.hrm.dto.payrollDetailsDTO.PayrollDetailsFilter;
import com.project.hrm.dto.payrollDetailsDTO.PayrollDetailsUpdateDTO;
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

    List<PayrollDetailsDTO> filterWithRange(BigDecimal minAmount, BigDecimal maxAmount, Float minPercentage, Float maxPercentage, int page, int size);
}
