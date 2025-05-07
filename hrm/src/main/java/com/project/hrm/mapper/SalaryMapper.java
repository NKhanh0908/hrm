package com.project.hrm.mapper;

import com.project.hrm.dto.salaryDTO.DetailSalaryDTO;
import com.project.hrm.dto.salaryDTO.SalaryDTO;
import com.project.hrm.dto.salaryDTO.SubsidyDTO;
import com.project.hrm.entities.DetailSalary;
import com.project.hrm.entities.Salary;
import com.project.hrm.entities.Subsidy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalaryMapper {

    // Convert entities to DTOs
    public SalaryDTO toSalaryDTO(Salary salary, DetailSalary detailSalary, List<Subsidy> subsidyList) {
        return SalaryDTO.builder()
                .id(salary.getId())
                .time(salary.getTime())
                .totalAmount(salary.getTotalAmount())
                .detailSalaryDTO(toDetailSalaryDTO(detailSalary))
                .subsidyDTOList(subsidyList != null
                        ? subsidyList.stream().map(this::toSubsidyDTO).collect(Collectors.toList())
                        : null)
                .build();
    }

    public DetailSalaryDTO toDetailSalaryDTO(DetailSalary detailSalary) {
        return DetailSalaryDTO.builder()
                .id(detailSalary.getId())
                .basicSalary(detailSalary.getBasicSalary())
                .build();
    }

    public SubsidyDTO toSubsidyDTO(Subsidy subtitle) {
        return SubsidyDTO.builder()
                .id(subtitle.getId())
                .typeSubsidy(subtitle.getTypeSubsidy())
                .amount(subtitle.getAmount())
                .build();
    }

    public Subsidy toSubsidy(SubsidyDTO subsidyDTO) {
        return Subsidy.builder()
        .id(subsidyDTO.getId())
        .typeSubsidy(subsidyDTO.getTypeSubsidy())
        .amount(subsidyDTO.getAmount())
        .build();
    }

    public DetailSalary toDetailSalary(DetailSalaryDTO detailSalaryDTO) {
        return DetailSalary.builder()
        .id(detailSalaryDTO.getId())
        .basicSalary(detailSalaryDTO.getBasicSalary())
        .build();
    }

    public Salary toSalary(SalaryDTO salaryDTO) {
        return Salary.builder()
        .id(salaryDTO.getId())
        .time(salaryDTO.getTime())
        .totalAmount(salaryDTO.getTotalAmount())
        .build();
    }
}
