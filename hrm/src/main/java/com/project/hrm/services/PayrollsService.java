package com.project.hrm.services;

import com.project.hrm.dto.payrollsDTO.*;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.PayPeriods;
import com.project.hrm.entities.Payrolls;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface PayrollsService {
    PayrollsDTO create(PayrollsCreateDTO payrollsCreateDTO);

    PayrollsDTO update(PayrollsUpdateDTO payrollsUpdateDTO);

    void delete(Integer Id);

    Boolean checkExistence(Integer Id);

    PayrollsDTO getById(Integer id);

    Payrolls getEntityById(Integer id);

    List<PayrollsDTO> filter(PayrollsFilter payrollFilter, int page, int size);

    List<PayrollsDTO> filterWithRange(PayrollsFilterWithRange payrollsFilterWithRange, int page, int size);

    PayrollsResponseDTO createPayrollForEmployee(PayrollsCreateDTO payrollsCreateDTO);

    List<PayrollsResponseDTO> createPayrollsForDepartment(Integer departmentId,PayrollsCreateDTO payrollsCreateDOTTemplate);

    List<PayrollsResponseDTO> createPayrollsForAllEmployee(PayrollsCreateDTO payrollsCreateDTOTemplate);
}
