package com.project.hrm.payrollManager.mapper;

import com.project.hrm.payrollManager.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.payrollManager.dto.payPeriodsDTO.PayPeriodsDTO;
import com.project.hrm.payrollManager.entities.PayPeriods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PayPeriodMapper {

    //Convert entities to DTOs
    public PayPeriodsDTO toPayPeriodDTO(PayPeriods payPeriods) {
        return PayPeriodsDTO.builder()
                .id(payPeriods.getId())
                .payPeriodCode(payPeriods.getPayPeriodCode())
                .startDate(payPeriods.getStartDate())
                .endDate(payPeriods.getEndDate())
                .status(payPeriods.getStatus())
                .build();
    }

    public PayPeriods toPayPeriods(PayPeriodsDTO payPeriodDTO) {
        return PayPeriods.builder()
                .id(payPeriodDTO.getId())
                .payPeriodCode(payPeriodDTO.getPayPeriodCode())
                .startDate(payPeriodDTO.getStartDate())
                .endDate(payPeriodDTO.getEndDate())
                .status(payPeriodDTO.getStatus())
                .build();
    }

    public PayPeriods toPayPeriodsFromCreateDTO(PayPeriodsCreateDTO payPeriodCreateDTO) {
        return PayPeriods.builder()
                .payPeriodCode(payPeriodCreateDTO.getPayPeriodCode())
                .startDate(payPeriodCreateDTO.getStartDate())
                .endDate(payPeriodCreateDTO.getEndDate())
                .status(payPeriodCreateDTO.getStatus())
                .build();
    }

    public List<PayPeriodsDTO> convertPageEntityToPageDTO(Page<PayPeriods> payPeriodsPage) {
        return payPeriodsPage.getContent()
                .stream()
                .map(this::toPayPeriodDTO)
                .collect(Collectors.toList());
    }
}
