package com.project.hrm.mapper;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.contractDTO.ContractCreateDTO;
import com.project.hrm.dto.contractDTO.ContractDTO;
import com.project.hrm.entities.Contracts;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
import com.project.hrm.enums.ContractStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ContractMapper {
    private final EmployeeMapper employeeMapper;

    public ContractDTO toDTO(Contracts contracts){
        return ContractDTO.builder()
                .id(contracts.getId())
                .title(contracts.getTitle())
                .baseSalary(contracts.getBaseSalary())
                .startDate(contracts.getStartDate())
                .endDate(contracts.getEndDate())
                .description(contracts.getDescription())
                .status(contracts.getContractStatus().toString())
                .contractSigningDate(contracts.getContractSigningDate())
                .employee(employeeMapper.toEmployeeDTO(contracts.getEmployee()))
                .departmentId(contracts.getRole().getDepartments().getId())
                .departmentName(contracts.getRole().getDepartments().getDepartmentName())
                .roleName(contracts.getRole().getName())
                .build();
    }

    public Contracts convertCreateDTOToEntity(ContractCreateDTO contractCreateDTO, Employees employees, Role role){
        return Contracts.builder()
                .title(contractCreateDTO.getTitle())
                .baseSalary(contractCreateDTO.getBaseSalary())
                .startDate(contractCreateDTO.getStartDate())
                .endDate(contractCreateDTO.getEndDate())
                .description(contractCreateDTO.getDescription())
                .contractStatus(ContractStatus.SIGNED)
                .contractSigningDate(contractCreateDTO.getContractSigningDate())
                .employee(employees)
                .role(role)
                .build();
    }

    public List<ContractDTO> convertPageToList(Page<Contracts> contractsPage){
        return  contractsPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<ContractDTO> toContractPageDTO(Page<Contracts> page) {
        return PageDTO.<ContractDTO>builder()
                .content(page.getContent()
                        .stream()
                        .map(this::toDTO)
                        .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
