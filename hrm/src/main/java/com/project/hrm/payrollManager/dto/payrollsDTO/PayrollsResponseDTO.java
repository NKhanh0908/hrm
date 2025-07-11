package com.project.hrm.payrollManager.dto.payrollsDTO;

import com.project.hrm.dto.attendanceDTO.AttendanceResponseForPayrollDTO;
import com.project.hrm.dto.dayOffDTO.DayOffResponseForPayrollDTO;
import com.project.hrm.payrollManager.dto.payrollComponentsDTO.PayrollComponentsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayrollsResponseDTO {
    private Integer payrollId;
    private Integer employeeId;
    private Integer payPeriodId;

    private Double baseSalary;

    private List<PayrollComponentsDTO> additions;
    private List<PayrollComponentsDTO> deductions;

    private AttendanceResponseForPayrollDTO attendance;
    private DayOffResponseForPayrollDTO dayOff;

    private int numberOfDependents;
    private BigDecimal dependentDeductionAmount;

    private BigDecimal grossSalary;
    private BigDecimal totalDeductions;
    private BigDecimal totalInsuranceAmount;
    private BigDecimal totalTaxAmount;
    private BigDecimal netSalary;
}
