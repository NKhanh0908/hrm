package com.project.hrm.payroll.mapper;

import com.project.hrm.payroll.dto.payrollComponentsDTO.PayrollComponentsCreateDTO;
import com.project.hrm.payroll.dto.payrollComponentsDTO.PayrollComponentsDTO;
import com.project.hrm.payroll.entities.PayrollComponents;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PayrollComponentMapper {

    public PayrollComponentsDTO toPayrollComponentsDTO(PayrollComponents payrollComponents) {
        return PayrollComponentsDTO.builder()
                .id(payrollComponents.getId())
                .name(payrollComponents.getName())
                .type(payrollComponents.getType())
                .amount(payrollComponents.getAmount())
                .isPercentage(payrollComponents.getIsPercentage()) // Thêm ánh xạ isPercentage
                .percentage(payrollComponents.getPercentage())
                .regulationId(
                        payrollComponents.getRegulation() != null ? payrollComponents.getRegulation().getId() : null
                )
                .payrollsId(
                        payrollComponents.getPayroll() != null ? payrollComponents.getPayroll().getId() : null
                )
                .build();
    }

    public PayrollComponents toPayrollComponents(PayrollComponentsDTO payrollComponentDTO) {
        return PayrollComponents.builder()
                .id(payrollComponentDTO.getId())
                .name(payrollComponentDTO.getName())
                .type(payrollComponentDTO.getType())
                .amount(payrollComponentDTO.getAmount())
                .isPercentage(payrollComponentDTO.getIsPercentage()) // Đảm bảo ánh xạ isPercentage
                .percentage(payrollComponentDTO.getPercentage())
                .build();
    }

    public PayrollComponents toPayrollComponentsFromCreateDTO(PayrollComponentsCreateDTO payrollComponentsCreateDTO) {
        return PayrollComponents.builder()
                .name(payrollComponentsCreateDTO.getName())
                .type(payrollComponentsCreateDTO.getType())
                .amount(payrollComponentsCreateDTO.getAmount())
                .isPercentage(payrollComponentsCreateDTO.getIsPercentage()) // Đảm bảo ánh xạ isPercentage
                .percentage(payrollComponentsCreateDTO.getPercentage())
                .build();
    }

    public List<PayrollComponentsDTO> convertPageEntityToPageDTO(Page<PayrollComponents> payrollComponentsPage) {
        return payrollComponentsPage.getContent()
                .stream()
                .map(this::toPayrollComponentsDTO)
                .collect(Collectors.toList());
    }
}