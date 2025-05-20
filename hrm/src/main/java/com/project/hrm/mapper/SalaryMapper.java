package com.project.hrm.mapper;

import com.project.hrm.dto.salaryDTO.SalaryCreateDTO;
import com.project.hrm.dto.salaryDTO.SalaryDTO;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Salary;


import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class SalaryMapper {

    private final EmployeeMapper employeeMapper;

    // Convert entities to DTOs
    public SalaryDTO toSalaryDTO(Salary salary) {
        return SalaryDTO.builder()
                .id(salary.getId())
                .time(salary.getTime())
                .employeeDTO(employeeMapper.toEmployeeDTO(salary.getEmployee()))
                .build();
    }

    public Salary toSalary(SalaryDTO salaryDTO) {
        return Salary.builder()
                .id(salaryDTO.getId())
                .time(salaryDTO.getTime())
                .employee(employeeMapper.toEntity(salaryDTO.getEmployeeDTO()))
                .build();
    }

    public Salary toSalaryFromCreateDTO(SalaryCreateDTO salaryCreateDTO, Employees employee){
        return Salary.builder()
                .time(salaryCreateDTO.getTime())
                .employee(employee)
                .build();
    }
    
    // public DetailSalaryDTO toDetailSalaryDTO(DetailSalary detailSalary) {
    //     return DetailSalaryDTO.builder()
    //             .id(detailSalary.getId())
    //             .basicSalary(detailSalary.getBasicSalary())
    //             .build();
    // }



    // public SubsidyDTO toSubsidyDTO(Subsidy subtitle) {
    //     return SubsidyDTO.builder()
    //             .id(subtitle.getId())
    //             .typeSubsidy(subtitle.getTypeSubsidy())
    //             .amount(subtitle.getAmount())
    //             .build();
    // }

    // public Subsidy toSubsidy(SubsidyDTO subsidyDTO) {
    //     return Subsidy.builder()
    //     .id(subsidyDTO.getId())
    //     .typeSubsidy(subsidyDTO.getTypeSubsidy())
    //     .amount(subsidyDTO.getAmount())
    //     .build();
    // }

    // public Subsidy subsidyCreateSubsidy(SubsidyCreateDTO scd){
    //     return Subsidy.builder()
    //     .typeSubsidy(scd.getTypeSubsidy())
    //     .amount(scd.getAmount())
    //     .salaryId()
    //     .build();
    // }

    // public DetailSalary toDetailSalary(DetailSalaryDTO detailSalaryDTO) {
    //     return DetailSalary.builder()
    //     .id(detailSalaryDTO.getId())
    //     .basicSalary(detailSalaryDTO.getBasicSalary())
    //     .build();
    // }

    // public DetailSalary detailCreateDetailSalary(DetailSalaryCreateDTO dscd){
    //     return DetailSalary.builder()
    //     .basicSalary(dscd.getBasicSalary())
    //     .salaryId(dscd.getSalaryId())
    //     .build();
    // }

    


}
