package com.project.hrm.mapper;

import com.project.hrm.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsDTO;
import com.project.hrm.entities.PayPeriods;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
}
