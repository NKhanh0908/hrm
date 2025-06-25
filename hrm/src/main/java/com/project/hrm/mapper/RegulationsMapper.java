package com.project.hrm.mapper;

import com.project.hrm.dto.regulationsDTO.RegulationsCreateDTO;
import com.project.hrm.dto.regulationsDTO.RegulationsDTO;
import com.project.hrm.entities.Regulations;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RegulationsMapper {
    //Convert entities to DTOs
    public RegulationsDTO toRegulationsDTO(Regulations regulations) {
        return RegulationsDTO.builder()
                .id(regulations.getId())
                .name(regulations.getName())
                .amount(regulations.getAmount())
                .percentage(regulations.getPercentage())
                .applicableSalary(regulations.getApplicableSalary())
                .effectiveDate(regulations.getEffectiveDate())
                .build();
    }

    //Convert DTOS to entities
    public Regulations toRegulations(RegulationsDTO dto) {
        return Regulations.builder()
                .id(dto.getId())
                .name(dto.getName())
                .amount(dto.getAmount())
                .percentage(dto.getPercentage())
                .applicableSalary(dto.getApplicableSalary())
                .effectiveDate(dto.getEffectiveDate())
                .build();
    }

    //To entities from CreateDTOs
    public Regulations toRegulationsFromCreateDTO(RegulationsCreateDTO createDTO) {
        return Regulations.builder()
                .name(createDTO.getName())
                .amount(createDTO.getAmount())
                .percentage(createDTO.getPercentage())
                .applicableSalary(createDTO.getApplicableSalary())
                .effectiveDate(createDTO.getEffectiveDate())
                .build();
    }

    public List<RegulationsDTO> convertPageEntityToPageDTO(Page<Regulations> pageRegulations) {
        return pageRegulations.getContent()
                .stream()
                .map(this::toRegulationsDTO)
                .collect(Collectors.toList());
    }
}
