package com.project.hrm.mapper;

import com.project.hrm.dto.salaryDTO.DeductionCreateDTO;
import com.project.hrm.dto.salaryDTO.DeductionDTO;
import com.project.hrm.entities.Deduction;
import com.project.hrm.entities.Salary;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeductionMapper {
    private final SalaryMapper salaryMapper;
    public DeductionDTO toDeductionDTO(Deduction deduction){
        return DeductionDTO.builder()
                .id(deduction.getId())
                .typeDeduction(deduction.getTypeDeduction())
                .amount(deduction.getAmount())
                .salaryDTO(salaryMapper.toSalaryDTO(deduction.getSalary()))
                .build();
    } 


    public Deduction toDeduction(DeductionDTO deductionDTO){
        return Deduction.builder()
                .id(deductionDTO.getId())
                .typeDeduction(deductionDTO.getTypeDeduction())
                .amount(deductionDTO.getAmount())
                .salary(salaryMapper.toSalary(deductionDTO.getSalaryDTO()))
                .build();
    }

    public Deduction toDeductionFromCreateDTO(DeductionCreateDTO deductionCreateDTO, Salary salary){
        return Deduction.builder()
                .typeDeduction(deductionCreateDTO.getTypeDeduction())
                .amount(deductionCreateDTO.getAmount())
                .salary(salary)
                .build();
    }
}
