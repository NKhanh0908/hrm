package com.project.hrm.services;

import com.project.hrm.dto.attendanceDTO.AttendanceResponseForPayrollDTO;
import com.project.hrm.dto.contractDTO.ContractDTO;
import com.project.hrm.dto.dayOffDTO.DayOffResponseForPayrollDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsResponseDTO;
import com.project.hrm.entities.PayPeriods;
import com.project.hrm.entities.Payrolls;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PayrollCalculationService {
    PayrollsResponseDTO createPayrollForEmployee(PayrollsCreateDTO payrollsCreateDTO);

    List<PayrollsResponseDTO> createBatchPayrolls(List<PayrollsCreateDTO> payrollsCreateDTOList);

    List<PayrollsResponseDTO> createBatchPayrollsByDepartment(Integer departmentId, PayrollsCreateDTO template);

    List<PayrollsResponseDTO> createBatchPayrollsForAllEmployees(PayrollsCreateDTO template);
}
