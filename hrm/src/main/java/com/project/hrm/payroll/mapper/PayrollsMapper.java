package com.project.hrm.payroll.mapper;

import com.project.hrm.payroll.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.payroll.dto.payrollsDTO.PayrollsDTO;
import com.project.hrm.payroll.entities.Payrolls;
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
                .status(payrollsDTO.getStatus())
                .build();
    }

    public PayrollsDTO toPayrollsDTO(Payrolls payrolls) {
        return PayrollsDTO.builder()
                .id(payrolls.getId())
                .employeeId(payrolls.getEmployee().getId())
                .payPeriodId(payrolls.getPayPeriod().getId())
                .status(payrolls.getStatus())
                .build();
    }

    public Payrolls toPayrollsFromCreateDTO(PayrollsCreateDTO payrollsCreateDTO) {
        return Payrolls.builder()
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
