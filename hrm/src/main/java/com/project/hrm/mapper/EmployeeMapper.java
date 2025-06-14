package com.project.hrm.mapper;

import com.project.hrm.dto.contractDTO.ContractDTO;
import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.entities.Contracts;
import com.project.hrm.entities.Employees;
import com.project.hrm.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EmployeeMapper {

    // Convert entity to DTO
    public EmployeeDTO toEmployeeDTO(Employees employees) {
        return EmployeeDTO.builder()
                .id(employees.getId())
                .firstName(employees.getFirstName())
                .lastName(employees.getLastName())
                .email(employees.getEmail())
                .phone(employees.getPhone())
                .gender(employees.getGender())
                .dateOfBirth(employees.getDateOfBirth())
                .citizenIdentificationCard(employees.getCitizenIdentificationCard())
                .address(employees.getAddress())
                .position(employees.getPosition())
                .image(employees.getImage())
                .status(employees.getStatus().toString())
                .build();
    }

    public List<EmployeeDTO> toEmployeeDTOList(List<Employees> employees) {
        return employees.stream()
                .map(this::toEmployeeDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> pageToEmployeeDTOList(Page<Employees> employeesPage) {
        return employeesPage.getContent()
                .stream()
                .map(this::toEmployeeDTO)
                .collect(Collectors.toList());
    }

    // Convert DTO to entity
    public Employees toEntity(EmployeeDTO employeeDTO) {
        return Employees.builder()
                .id(employeeDTO.getId())
                .firstName(employeeDTO.getFirstName())
                .status(EmployeeStatus.valueOf(employeeDTO.getStatus()))
                .lastName(employeeDTO.getLastName())
                .email(employeeDTO.getEmail())
                .phone(employeeDTO.getPhone())
                .gender(employeeDTO.getGender())
                .dateOfBirth(employeeDTO.getDateOfBirth())
                .citizenIdentificationCard(employeeDTO.getCitizenIdentificationCard())
                .address(employeeDTO.getAddress())
                .position(employeeDTO.getPosition())
                .image(employeeDTO.getImage())
                .build();
    }

    public Employees employeeCreateToEmployee(EmployeeCreateDTO dto) {
        return Employees.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .status(EmployeeStatus.ACTIVE)
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .dateOfBirth(dto.getDateOfBirth())
                .citizenIdentificationCard(dto.getCitizenIdentificationCard())
                .address(dto.getAddress())
                .position(dto.getPosition())
                .build();
    }

    public Employees employeeUpdateToEmployee(EmployeeUpdateDTO dto) {
        return Employees.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .dateOfBirth(dto.getDateOfBirth())
                .citizenIdentificationCard(dto.getCitizenIdentificationCard())
                .address(dto.getAddress())
                .build();
    }

    public ContractDTO toContactDTO(Contracts contracts) {
        return ContractDTO.builder()
                .id(contracts.getId())
                .description(contracts.getDescription())
                .baseSalary(contracts.getBaseSalary())
                .title(contracts.getTitle())
                .contractSigningDate(contracts.getContractSigningDate())
                .startDate(contracts.getStartDate())
                .endDate(contracts.getEndDate())
                .build();
    }
}
