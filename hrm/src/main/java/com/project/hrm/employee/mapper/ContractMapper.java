package com.project.hrm.employee.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.contractDTO.ContractCreateDTO;
import com.project.hrm.employee.dto.contractDTO.ContractDTO;
import com.project.hrm.employee.entity.Contracts;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.department.entity.Role;
import com.project.hrm.employee.enums.ContractStatus;
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

    public Contracts convertCreateDTOToEntity(ContractCreateDTO contractCreateDTO, Employees employees, Role role) {
        return Contracts.builder()
                .title(contractCreateDTO.getTitle() != null ? contractCreateDTO.getTitle() : "Untitled Contract")
                .baseSalary(contractCreateDTO.getBaseSalary() != null ? contractCreateDTO.getBaseSalary() : 0.0)
                .startDate(contractCreateDTO.getStartDate() != null ? contractCreateDTO.getStartDate() : null)
                .endDate(contractCreateDTO.getEndDate() != null ? contractCreateDTO.getEndDate() : null)
                .description(contractCreateDTO.getDescription() != null ? contractCreateDTO.getDescription() : "")
                .contractStatus(ContractStatus.SIGNED)
                .contractSigningDate(contractCreateDTO.getContractSigningDate() != null ? contractCreateDTO.getContractSigningDate() : null)
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
