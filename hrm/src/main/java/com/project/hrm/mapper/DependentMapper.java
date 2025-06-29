package com.project.hrm.mapper;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.dependentDTO.DependentCreateDTO;
import com.project.hrm.dto.dependentDTO.DependentDTO;
import com.project.hrm.entities.Dependent;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DependentMapper {
    public Dependent toEntity(DependentDTO dependentDTO) {
        return Dependent.builder()
                .id(dependentDTO.getId())
                .name(dependentDTO.getName())
                .relationship(dependentDTO.getRelationship())
                .birthDate(dependentDTO.getBirthDate())
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
                .build();
    }

    public PageDTO<DependentDTO> toDependentPageDTO(Page<Dependent> page) {
        return PageDTO.<DependentDTO>builder()
                .content(
                        page.getContent()
                                .stream()
                                .map(this::toDTO)
                                .toList()
                )
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
