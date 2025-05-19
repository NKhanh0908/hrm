package com.project.hrm.mapper;

import com.project.hrm.dto.contractDTO.ContractCreateDTO;
import com.project.hrm.dto.contractDTO.ContractDTO;
import com.project.hrm.entities.Contracts;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractMapper {
    public ContractDTO toDTO(Contracts contracts){
        return ContractDTO.builder()
                .id(contracts.getId())
                .title(contracts.getTitle())
                .baseSalary(contracts.getBaseSalary())
                .startDate(contracts.getStartDate())
                .endDate(contracts.getEndDate())
                .description(contracts.getDescription())
                .contractSigningDate(contracts.getContractSigningDate())
                .employeeId(contracts.getEmployee().getId())
                .employeeName(contracts.getEmployee().fullName())
                .departmentId(contracts.getDepartments().getId())
                .departmentName(contracts.getDepartments().getDepartmentName())
                .roleName(contracts.getRole().getName())
                .build();
    }

    public Contracts convertCreateDTOToEntity(ContractCreateDTO contractCreateDTO, Employees employees, Departments departments, Role role){
        return Contracts.builder()
                .title(contractCreateDTO.getTitle())
                .baseSalary(contractCreateDTO.getBaseSalary())
                .startDate(contractCreateDTO.getStartDate())
                .endDate(contractCreateDTO.getEndDate())
                .description(contractCreateDTO.getDescription())
                .contractSigningDate(contractCreateDTO.getContractSigningDate())
                .employee(employees)
                .departments(departments)
                .role(role)
                .build();
    }

    public List<ContractDTO> convertPageToList(Page<Contracts> contractsPage){
        return  contractsPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
