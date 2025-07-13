package com.project.hrm.payroll.mapper;

import com.project.hrm.payroll.dto.regulationsDTO.RegulationsCreateDTO;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsDTO;
import com.project.hrm.payroll.entities.Regulations;
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
        if (regulations == null) {
            throw new IllegalArgumentException("Regulations entity cannot be null");
        }
        return RegulationsDTO.builder()
                .id(regulations.getId())
                .name(regulations.getName())
                .type(regulations.getType())
                .amount(regulations.getAmount() != null ? regulations.getAmount() : null)
                .percentage(regulations.getPercentage() != null ? regulations.getPercentage() : null)
                .applicableSalary(regulations.getApplicableSalary())
                .effectiveDate(regulations.getEffectiveDate())
                .build();
    }

    //Convert DTOS to entities
    public Regulations toRegulations(RegulationsDTO dto) {
        return Regulations.builder()
                .id(dto.getId())
                .name(dto.getName())
                .type(dto.getType())
                .amount(dto.getAmount() != null ? dto.getAmount() : null)
                .percentage(dto.getPercentage() != null ? dto.getPercentage() : null)
                .applicableSalary(dto.getApplicableSalary())
                .effectiveDate(dto.getEffectiveDate())
                .build();
    }

    //To entities from CreateDTOs
    public Regulations toRegulationsFromCreateDTO(RegulationsCreateDTO createDTO) {
        return Regulations.builder()
                .regulationKey(createDTO.getRegulationKey())
                .name(createDTO.getName())
                .type(createDTO.getType())
                .amount(createDTO.getAmount() != null ? createDTO.getAmount() : null)
                .percentage(createDTO.getPercentage() != null ? createDTO.getPercentage() : null)
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
