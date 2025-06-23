package com.project.hrm.mapper;

import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsCreateDTO;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsDTO;
import com.project.hrm.entities.PayrollComponents;
import com.project.hrm.repositories.RegulationsRepository;
import com.project.hrm.services.RegulationsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PayrollComponentMapper {
    private RegulationsService regulationsService;

    //Convert entities to DTOs
    public PayrollComponentsDTO toPayrollComponentsDTO(PayrollComponents payrollComponents) {
        return PayrollComponentsDTO.builder()
                .id(payrollComponents.getId())
                .name(payrollComponents.getName())
                .type(payrollComponents.getType())
                .amount(payrollComponents.getAmount())
                .percentage(payrollComponents.getPercentage())
                .regulationId(
                        payrollComponents.getRegulation() != null ? payrollComponents.getRegulation().getId() : null
                )
                .build();
    }

    //Convert DTOS to entities
    public PayrollComponents toPayrollComponents(PayrollComponentsDTO payrollComponentDTO) {
        return PayrollComponents.builder()
                .id(payrollComponentDTO.getId())
                .name(payrollComponentDTO.getName())
                .type(payrollComponentDTO.getType())
                .amount(payrollComponentDTO.getAmount())
                .percentage(payrollComponentDTO.getPercentage())
                .regulation(
                        payrollComponentDTO.getRegulationId() != null
                                ? regulationsService.getEntityById(payrollComponentDTO.getRegulationId())
                                : null
                )
                .build();
    }

    public PayrollComponents toPayrollComponentsFromCreateDTO(PayrollComponentsCreateDTO payrollComponentsCreateDTO) {
        return PayrollComponents.builder()
                .name(payrollComponentsCreateDTO.getName())
                .type(payrollComponentsCreateDTO.getType())
                .amount(payrollComponentsCreateDTO.getAmount())
                .percentage(payrollComponentsCreateDTO.getPercentage())
                .regulation(
                        payrollComponentsCreateDTO.getRegulationId() != null
                        ? regulationsService.getEntityById(payrollComponentsCreateDTO.getRegulationId())
                        : null
                )
                .build();
    }

    public List<PayrollComponentsDTO> convertPageEntityToPageDTO(Page<PayrollComponents> payrollComponentsPage) {
        return payrollComponentsPage.getContent()
                .stream()
                .map(this::toPayrollComponentsDTO)
                .collect(Collectors.toList());
    }
}
