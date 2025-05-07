package com.project.hrm.mapper;

import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.dto.contractDTO.ContractDTO;
import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.entities.Contracts;
import com.project.hrm.entities.Employees;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EmployeeMapper {

    private final DepartmentMapper departmentMapper;


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
                .departmentDTO(departmentMapper.toDepartmentDTO(employees.getDepartment()))
                .build();
    }

    public List<EmployeeDTO> toEmployeeDTOList(List<Employees> employees) {
        return employees.stream()
                .map(this::toEmployeeDTO)
                .collect(Collectors.toList());
    }

    public Page<EmployeeDTO> pageToEmployeeDTOList(Page<Employees> employees) {
        List<EmployeeDTO> dtoList = employees.stream()
                .map(this::toEmployeeDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, employees.getPageable(), employees.getTotalElements());
    }

    // Convert DTO to entity
    public Employees toEntity(EmployeeDTO employeeDTO) {
        return Employees.builder()
                .id(employeeDTO.getId())
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .email(employeeDTO.getEmail())
                .phone(employeeDTO.getPhone())
                .gender(employeeDTO.getGender())
                .dateOfBirth(employeeDTO.getDateOfBirth())
                .citizenIdentificationCard(employeeDTO.getCitizenIdentificationCard())
                .address(employeeDTO.getAddress())
                .department(departmentMapper.toDepartment(employeeDTO.getDepartmentDTO()))
                .build();
    }

    public Employees employeeCreateToEmployee(EmployeeCreateDTO dto, DepartmentDTO departmentDTO) {
        return Employees.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .dateOfBirth(dto.getDateOfBirth())
                .citizenIdentificationCard(dto.getCitizenIdentificationCard())
                .address(dto.getAddress())
                .department(departmentMapper.toDepartment(departmentDTO))
                .build();
    }

    public Employees employeeUpdateToEmployee(EmployeeUpdateDTO dto, DepartmentDTO departmentDTO) {
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
                .department(departmentMapper.toDepartment(departmentDTO))
                .build();
    }

    // Convert contract entity to DTO (không dùng builder)
    public ContractDTO toContactDTO(Contracts contracts) {
        return ContractDTO.builder()
                .id(contracts.getId())
                .description(contracts.getDescription())
                .baseSalary(contracts.getBaseSalary())
                .title(contracts.getTitle())
                .contractSigningDate(contracts.getContractSigningDate())
                .startDate(contracts.getStartDate())
                .endDate(contracts.getEndDate())
                .employeeDTO(toEmployeeDTO(contracts.getEmployee()))
                .build();
    }
}
