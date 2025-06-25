package com.project.hrm.mapper;

import com.project.hrm.dto.payrollDetailsDTO.PayrollDetailsCreateDTO;
import com.project.hrm.dto.payrollDetailsDTO.PayrollDetailsDTO;
import com.project.hrm.entities.PayrollDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PayrollDetailsMapper {

    public PayrollDetails toEntity(PayrollDetailsDTO payrollDetailsDTO) {
        return PayrollDetails.builder()
                .id(payrollDetailsDTO.getId())
                .amount(payrollDetailsDTO.getAmount())
                .isPercentage(payrollDetailsDTO.getIsPercentage())
                .percentage(payrollDetailsDTO.getPercentage())
                .build();
    }

    public PayrollDetailsDTO toDTO(PayrollDetails payrollDetails) {
        return PayrollDetailsDTO.builder()
                .id(payrollDetails.getId())
                .payrollId(payrollDetails.getPayroll().getId())
                .payrollComponentId(payrollDetails.getPayrollComponent().getId())
                .amount(payrollDetails.getAmount())
                .isPercentage(payrollDetails.getIsPercentage())
                .percentage(payrollDetails.getPercentage())
                .build();
    }

    public PayrollDetails toPayrollDetailsFromCreateDTO(PayrollDetailsCreateDTO payrollDetailsCreateDTO) {
        return PayrollDetails.builder()
                .amount(payrollDetailsCreateDTO.getAmount())
                .isPercentage(payrollDetailsCreateDTO.getIsPercentage())
                .percentage(payrollDetailsCreateDTO.getPercentage())
                .build();
    }
}
