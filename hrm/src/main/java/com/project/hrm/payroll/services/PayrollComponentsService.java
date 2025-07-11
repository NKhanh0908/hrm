package com.project.hrm.payroll.services;

import com.project.hrm.payroll.dto.payrollComponentsDTO.*;
import com.project.hrm.payroll.entities.PayrollComponents;
import com.project.hrm.payroll.entities.Payrolls;
import com.project.hrm.payroll.enums.PayrollComponentType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    List<PayrollComponents> createComponents(Payrolls payrolls);

    PayrollComponents getPayrollComponentByPayrollIdAndType(Integer payrollId, PayrollComponentType type);

    Map<Integer, List<PayrollComponents>> createBatchComponents(List<Payrolls> payrolls);
}
