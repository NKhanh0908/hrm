package com.project.hrm.mapper;

import com.project.hrm.dto.payrollDetailsDTO.PayrollDetailsCreateDTO;
import com.project.hrm.dto.payrollDetailsDTO.PayrollDetailsDTO;
import com.project.hrm.entities.PayrollDetails;
import com.project.hrm.entities.Payrolls;
import com.project.hrm.services.PayrollComponentsService;
import com.project.hrm.services.PayrollsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PayrollDetailsMapper {
    private final PayrollsService payrollsService;
    private final PayrollComponentsService payrollComponentsService;


    public PayrollDetails toEntity(PayrollDetailsDTO payrollDetailsDTO) {
        return PayrollDetails.builder()
                .id(payrollDetailsDTO.getId())
                .payroll(payrollsService.getEntityById(payrollDetailsDTO.getPayrollComponentId()))
                .payrollComponent(payrollComponentsService.getEntityById(payrollDetailsDTO.getPayrollComponentId()))
                .amount(payrollDetailsDTO.getAmount())
                .is_percentage(payrollDetailsDTO.getIsPercentage())
                .percentage(payrollDetailsDTO.getPercentage())
                .build();
    }

    public PayrollDetailsDTO toDTO(PayrollDetails payrollDetails) {
        return PayrollDetailsDTO.builder()
                .id(payrollDetails.getId())
                .payrollId(payrollDetails.getPayroll().getId())
                .payrollComponentId(payrollDetails.getPayrollComponent().getId())
                .amount(payrollDetails.getAmount())
                .isPercentage(payrollDetails.getIs_percentage())
                .percentage(payrollDetails.getPercentage())
                .build();
    }

    public PayrollDetails toPayrollDetailsFromCreateDTO(PayrollDetailsCreateDTO payrollDetailsCreateDTO) {
        return PayrollDetails.builder()
                .payroll(payrollsService.getEntityById(payrollDetailsCreateDTO.getPayrollId()))
                .payrollComponent(payrollComponentsService.getEntityById(payrollDetailsCreateDTO.getPayrollComponentId()))
                .amount(payrollDetailsCreateDTO.getAmount())
                .is_percentage(payrollDetailsCreateDTO.getIsPercentage())
                .percentage(payrollDetailsCreateDTO.getPercentage())
                .build();
    }
}
