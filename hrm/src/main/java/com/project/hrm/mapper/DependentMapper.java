package com.project.hrm.mapper;

import com.project.hrm.dto.dependentDTO.DependentCreateDTO;
import com.project.hrm.dto.dependentDTO.DependentDTO;
import com.project.hrm.entities.Dependent;
import com.project.hrm.repositories.EmployeeRepository;
import com.project.hrm.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DependentMapper {
    private final EmployeeService employeeService;
    public Dependent toEntity(DependentDTO dependentDTO) {
        return Dependent.builder()
                .id(dependentDTO.getId())
                .name(dependentDTO.getName())
                .relationship(dependentDTO.getRelationship())
                .birthDate(dependentDTO.getBirthDate())
                .employee(employeeService.getEntityById(dependentDTO.getEmployeeId()))
                .build();
    }

    public DependentDTO toDTO(Dependent dependent) {
        return DependentDTO.builder()
                .id(dependent.getId())
                .name(dependent.getName())
                .relationship(dependent.getRelationship())
                .birthDate(dependent.getBirthDate())
                .employeeId(dependent.getEmployee().getId())
                .build();
    }

    public Dependent toEntityFromCreateDTO(DependentCreateDTO dependentCreateDTO) {
        return Dependent.builder()
                .name(dependentCreateDTO.getName())
                .relationship(dependentCreateDTO.getRelationship())
                .birthDate(dependentCreateDTO.getBirthDate())
                .employee(employeeService.getEntityById(dependentCreateDTO.getEmployeeId()))
                .build();
    }
}
