package com.project.hrm.employee.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.contractDTO.ContractDTO;
import com.project.hrm.employee.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.employee.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.employee.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.employee.entity.Contracts;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.employee.enums.EmployeeStatus;
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
                .firstName(employees.getFirstName() != null ? employees.getFirstName() : "Unknown")
                .lastName(employees.getLastName() != null ? employees.getLastName() : null)
                .email(employees.getEmail() != null ? employees.getEmail() : null)
                .phone(employees.getPhone() != null ? employees.getPhone() : null)
                .gender(employees.getGender() != null ? employees.getGender() : "OTHER")
                .dateOfBirth(employees.getDateOfBirth() != null ? employees.getDateOfBirth() : null)
                .citizenIdentificationCard(employees.getCitizenIdentificationCard() != null ? employees.getCitizenIdentificationCard() : null)
                .address(employees.getAddress() != null ? employees.getAddress() : null)
                .image(employees.getImage() != null ? employees.getImage() : null)
                .status(employees.getStatus().toString())
                .roleId(employees.getRole() != null ? employees.getRole().getId() : null)
                .roleName(employees.getRole() != null ? employees.getRole().getName() : null)
                .departmentId(employees.getRole() != null ? employees.getRole().getDepartments().getId() : null)
                .departmentName(employees.getRole() != null ? employees.getRole().getDepartments().getDepartmentName() : null)
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

    public PageDTO<EmployeeDTO> toEmployeePageDTO(Page<Employees> employeesPage) {
        return PageDTO.<EmployeeDTO>builder()
                .content(
                        employeesPage.getContent()
                                .stream()
                                .map(this::toEmployeeDTO)
                                .toList()
                )
                .page(employeesPage.getNumber())
                .size(employeesPage.getSize())
                .totalElements(employeesPage.getTotalElements())
                .totalPages(employeesPage.getTotalPages())
                .build();
    }

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
                .image(employeeDTO.getImage())
                .build();
    }

    public Employees employeeCreateToEmployee(EmployeeCreateDTO dto) {
        return Employees.builder()
                .firstName(dto.getFirstName() != null ? dto.getFirstName() : "Unknown")
                .lastName(dto.getLastName() != null ? dto.getLastName() : null)
                .status(EmployeeStatus.PROBATION)
                .email(dto.getEmail() != null ? dto.getEmail() : null)
                .phone(dto.getPhone() != null ? dto.getPhone() : null)
                .gender(dto.getGender() != null ? dto.getGender() : null)
                .dateOfBirth(dto.getDateOfBirth() != null ? dto.getDateOfBirth() : null)
                .citizenIdentificationCard(dto.getCitizenIdentificationCard() != null ? dto.getCitizenIdentificationCard() : null)
                .address(dto.getAddress() != null ? dto.getAddress() : null)
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
