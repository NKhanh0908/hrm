package com.project.hrm.mapper;

import com.project.hrm.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsDTO;
import com.project.hrm.entities.Payrolls;
import com.project.hrm.repositories.EmployeeRepository;
import com.project.hrm.repositories.PayPeriodsRepository;
import com.project.hrm.repositories.PayrollsRepository;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.PayPeriodsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PayrollsMapper {
    private final EmployeeService employeeService;
    private final PayPeriodsService payPeriodsService;


    public Payrolls toPayrolls(PayrollsDTO payrollsDTO) {
        return Payrolls.builder()
                .id(payrollsDTO.getId())
                .employee(employeeService.getEntityById(payrollsDTO.getEmployeeId()))
                .payPeriod(payPeriodsService.getEntityById(payrollsDTO.getPayPeriodId()))
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
                .employee(employeeService.getEntityById(payrollsCreateDTO.getEmployeeId()))
                .payPeriod(payPeriodsService.getEntityById(payrollsCreateDTO.getPayPeriodId()))
                .total_income(payrollsCreateDTO.getTotalIncome())
                .total_deduction(payrollsCreateDTO.getTotalDeduction())
                .net_salary(payrollsCreateDTO.getNetSalary())
                .status(payrollsCreateDTO.getStatus())
                .build();
    }

    public List<PayrollsDTO> convertToPageEntityToPageDTO(Page<Payrolls> payrollsPage) {
        return payrollsPage.getContent()
                .stream()
                .map(this::toPayrollsDTO)
                .collect(Collectors.toList());
    }

}
