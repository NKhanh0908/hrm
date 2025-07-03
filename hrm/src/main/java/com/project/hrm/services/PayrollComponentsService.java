package com.project.hrm.services;

import com.project.hrm.dto.payrollComponentsDTO.*;
import com.project.hrm.entities.PayrollComponents;
import com.project.hrm.entities.Payrolls;
import com.project.hrm.entities.Regulations;
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

    List<PayrollComponentsDTO> filterWithRange(PayrollComponentsFilterWithRange payrollComponentsFilterWithRange, int page, int size);

    List<PayrollComponentsDTO> createComponents(Payrolls payrolls);

    PayrollComponents getPayrollComponentByPayrollIdAndType(Integer payrollId, PayrollComponentType type);
}
