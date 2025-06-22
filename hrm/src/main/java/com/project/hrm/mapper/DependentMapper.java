package com.project.hrm.mapper;

import com.project.hrm.dto.dependentDTO.DependentCreateDTO;
import com.project.hrm.dto.dependentDTO.DependentDTO;
import com.project.hrm.entities.Dependent;
import com.project.hrm.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DependentMapper {
    private final EmployeeRepository employeeRepository;

    public Dependent toEntity(DependentDTO dependentDTO) {
        return Dependent.builder()
                .id(dependentDTO.getId())
                .name(dependentDTO.getName())
                .relationship(dependentDTO.getRelationship())
                .birthDate(dependentDTO.getBirthDate())
                .employee(employeeRepository.findById(dependentDTO.getEmployeeId())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + dependentDTO.getEmployeeId())))
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
                .employee(employeeRepository.findById(dependentCreateDTO.getEmployeeId())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + dependentCreateDTO.getEmployeeId())))
                .build();
    }
}
