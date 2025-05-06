package com.project.hrm.mapper;

import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.dto.contractDTO.ContractDTO;
import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.entities.Contracts;
import com.project.hrm.entities.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {
    private final DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    // TODO: convert entities to dto

    public EmployeeDTO toEmployeeDTO(Employees employees) {
        DepartmentDTO departmentDTO = departmentMapper.toDepartmentDTO(employees.getDepartment());
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
                .departmentDTO(departmentDTO)
                .build();
    }

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

    public ContractDTO toContactDTO(Contracts contracts) {
        ContractDTO contactDTO = new ContractDTO();
        contactDTO.setId(contracts.getId());
        contactDTO.setDescription(contracts.getDescription());
        contactDTO.setBaseSalary(contracts.getBaseSalary());
        contactDTO.setTitle(contracts.getTitle());
        contactDTO.setContractSigningDate(contracts.getContractSigningDate());
        contactDTO.setStartDate(contracts.getStartDate());
        contactDTO.setEndDate(contracts.getEndDate());
        contactDTO.setEmployeeDTO(toEmployeeDTO(contracts.getEmployee()));
        return contactDTO;
    }

    public List<EmployeeDTO> toEmployeeDTOList(List<Employees> employees) {
        return employees.stream().map(this::toEmployeeDTO).collect(Collectors.toList());
    }

    public Page<EmployeeDTO> pageToEmployeeDTOList(Page<Employees> employees) {
        List<EmployeeDTO> dtoList = employees
                .stream()
                .map(this::toEmployeeDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, employees.getPageable(), employees.getTotalElements());
    }

    public Employees employeeCreateToEmployee(EmployeeCreateDTO employeeCreateDTO, DepartmentDTO departmentDTO) {
        return Employees.builder()
                .firstName(employeeCreateDTO.getFirstName())
                .lastName(employeeCreateDTO.getLastName())
                .email(employeeCreateDTO.getEmail())
                .phone(employeeCreateDTO.getPhone())
                .gender(employeeCreateDTO.getGender())
                .dateOfBirth(employeeCreateDTO.getDateOfBirth())
                .citizenIdentificationCard(employeeCreateDTO.getCitizenIdentificationCard())
                .address(employeeCreateDTO.getAddress())
                .department(departmentMapper.toDepartment(departmentDTO))
                .build();
    }

    public Employees employeeUpdateToEmployee(EmployeeUpdateDTO employeeUpdateDTO, DepartmentDTO departmentDTO) {
        return Employees.builder().build();
    }

}
