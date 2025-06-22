package com.project.hrm.mapper;

import com.project.hrm.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsDTO;
import com.project.hrm.entities.Payrolls;
import com.project.hrm.repositories.EmployeeRepository;
import com.project.hrm.repositories.PayPeriodsRepository;
import com.project.hrm.repositories.PayrollsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PayrollsMapper {
    private final EmployeeRepository employeeRepository;
    private final PayPeriodsRepository payPeriodsRepository;


    public Payrolls toPayrolls(PayrollsDTO payrollsDTO) {
        return Payrolls.builder()
                .id(payrollsDTO.getId())
                .employee(employeeRepository.findById(payrollsDTO.getEmployeeId())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID = " + payrollsDTO.getEmployeeId())))
                .payPeriod(payPeriodsRepository.findById(payrollsDTO.getPayPeriodId())
                        .orElseThrow(() -> new EntityNotFoundException("Pay Period not found with ID = " + payrollsDTO.getPayPeriodId())))
                .total_income(payrollsDTO.getTotalIncome())
                .total_deduction(payrollsDTO.getTotalDeduction())
                .net_salary(payrollsDTO.getNetSalary())
                .status(payrollsDTO.getStatus())
                .build();
    }

    public PayrollsDTO toPayrollsDTO(Payrolls payrolls) {
        return PayrollsDTO.builder()
                .id(payrolls.getId())
                .employeeId(payrolls.getEmployee().getId())
                .payPeriodId(payrolls.getPayPeriod().getId())
                .totalIncome(payrolls.getTotal_income())
                .totalDeduction(payrolls.getTotal_deduction())
                .netSalary(payrolls.getNet_salary())
                .status(payrolls.getStatus())
                .build();
    }

    public Payrolls toPayrrollsFromCreateDTO(PayrollsCreateDTO payrollsCreateDTO) {
        return Payrolls.builder()
                .employee(employeeRepository.findById(payrollsCreateDTO.getEmployeeId())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID = " + payrollsCreateDTO.getEmployeeId())))
                .payPeriod(payPeriodsRepository.findById(payrollsCreateDTO.getPayPeriodId())
                        .orElseThrow(() -> new EntityNotFoundException("Pay Period not found with ID = " + payrollsCreateDTO.getPayPeriodId())))
                .total_income(payrollsCreateDTO.getTotalIncome())
                .total_deduction(payrollsCreateDTO.getTotalDeduction())
                .net_salary(payrollsCreateDTO.getNetSalary())
                .status(payrollsCreateDTO.getStatus())
                .build();
    }

}
