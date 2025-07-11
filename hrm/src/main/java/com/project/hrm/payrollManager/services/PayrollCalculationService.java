package com.project.hrm.payrollManager.services;

import com.project.hrm.payrollManager.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.payrollManager.dto.payrollsDTO.PayrollsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PayrollCalculationService {
    PayrollsResponseDTO createPayrollForEmployee(PayrollsCreateDTO payrollsCreateDTO);

    List<PayrollsResponseDTO> createBatchPayrolls(List<PayrollsCreateDTO> payrollsCreateDTOList);

    List<PayrollsResponseDTO> createBatchPayrollsByDepartment(Integer departmentId, PayrollsCreateDTO template);

    List<PayrollsResponseDTO> createBatchPayrollsForAllEmployees(PayrollsCreateDTO template);
}
