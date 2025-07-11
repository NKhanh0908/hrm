package com.project.hrm.payrollManager.services;

import com.project.hrm.payrollManager.dto.payrollsDTO.*;
import com.project.hrm.payrollManager.entities.Payrolls;
import org.springframework.stereotype.Service;

import java.util.List;

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
