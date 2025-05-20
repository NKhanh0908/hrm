package com.project.hrm.mapper;

import org.springframework.stereotype.Component;

import com.project.hrm.dto.salaryDTO.SubsidyCreateDTO;
import com.project.hrm.dto.salaryDTO.SubsidyDTO;
import com.project.hrm.entities.Salary;
import com.project.hrm.entities.Subsidy;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SubsidyMapper {

    private final SalaryMapper salaryMapper;
    public SubsidyDTO toSubsidyDTO(Subsidy subsidy){
        return SubsidyDTO.builder()
                .id(subsidy.getId())
                .typeSubsidy(subsidy.getTypeSubsidy())
                .amount(subsidy.getAmount())
                .salaryDTO(salaryMapper.toSalaryDTO(subsidy.getSalary()))
                .build();
                
    } 


    public Subsidy toSubsidy(SubsidyDTO subsidyDTO){
        return Subsidy.builder()
                .id(subsidyDTO.getId())
                .typeSubsidy(subsidyDTO.getTypeSubsidy())
                .amount(subsidyDTO.getAmount())
                .salary(salaryMapper.toSalary(subsidyDTO.getSalaryDTO()))
                .build();
    }

    public Subsidy toSubsidyFromCreateDTO(SubsidyCreateDTO subsidyCreateDTO, Salary salary){
        return Subsidy.builder()
                .typeSubsidy(subsidyCreateDTO.getTypeSubsidy())
                .amount(subsidyCreateDTO.getAmount())
                .salary(salary)
                .build();
    }
}
