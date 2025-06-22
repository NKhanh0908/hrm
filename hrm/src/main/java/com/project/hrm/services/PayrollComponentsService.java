package com.project.hrm.services;

import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsCreateDTO;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsDTO;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsFilter;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsUpdateDTO;
import com.project.hrm.entities.PayrollComponents;
import com.project.hrm.enums.PayrollComponentType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface PayrollComponentsService {
    PayrollComponentsDTO create(PayrollComponentsCreateDTO payrollComponentsCreateDTO);

    PayrollComponentsDTO update(PayrollComponentsUpdateDTO payrollComponentsUpdateDTO);

    void delete(Integer Id);

    Boolean checkExistence(Integer Id);

    PayrollComponentsDTO getById(Integer id);

    PayrollComponents getEntityById(Integer id);

    List<PayrollComponentsDTO> filter(PayrollComponentsFilter payrollComponentFilter, int page, int size);

    List<PayrollComponentsDTO> filterWithRange(BigDecimal minAmount, BigDecimal maxAmount, Float minPercentage, Float maxPercentage, PayrollComponentType type, int page, int size);
}
