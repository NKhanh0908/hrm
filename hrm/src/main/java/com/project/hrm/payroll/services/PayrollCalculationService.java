package com.project.hrm.payroll.services;

import com.project.hrm.payroll.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.payroll.dto.payrollsDTO.PayrollsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PayrollCalculationService {
    PayrollsResponseDTO createPayrollForEmployee(PayrollsCreateDTO payrollsCreateDTO);

    List<PayrollsResponseDTO> createBatchPayrolls(List<PayrollsCreateDTO> payrollsCreateDTOList);

    List<PayrollsResponseDTO> createBatchPayrollsByDepartment(Integer departmentId, PayrollsCreateDTO template);

    List<PayrollsResponseDTO> createBatchPayrollsForAllEmployees(PayrollsCreateDTO template);
}
