package com.project.hrm.mapper;

import com.project.hrm.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsDTO;
import com.project.hrm.entities.Payrolls;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.PayPeriodsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PayrollsMapper {

    public Payrolls toPayrolls(PayrollsDTO payrollsDTO) {
        return Payrolls.builder()
                .id(payrollsDTO.getId())
                .totalIncome(payrollsDTO.getTotalIncome())
                .totalDeduction(payrollsDTO.getTotalDeduction())
                .netSalary(payrollsDTO.getNetSalary())
                .status(payrollsDTO.getStatus())
                .build();
    }

    public PayrollsDTO toPayrollsDTO(Payrolls payrolls) {
        return PayrollsDTO.builder()
                .id(payrolls.getId())
                .employeeId(payrolls.getEmployee().getId())
                .payPeriodId(payrolls.getPayPeriod().getId())
                .totalIncome(payrolls.getTotalIncome())
                .totalDeduction(payrolls.getTotalDeduction())
                .netSalary(payrolls.getNetSalary())
                .status(payrolls.getStatus())
                .build();
    }

    public Payrolls toPayrollsFromCreateDTO(PayrollsCreateDTO payrollsCreateDTO) {
        return Payrolls.builder()
                .totalIncome(payrollsCreateDTO.getTotalIncome())
                .totalDeduction(payrollsCreateDTO.getTotalDeduction())
                .netSalary(payrollsCreateDTO.getNetSalary())
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
