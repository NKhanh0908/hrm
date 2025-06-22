package com.project.hrm.mapper;

import com.project.hrm.dto.regulationsDTO.RegulationsCreateDTO;
import com.project.hrm.dto.regulationsDTO.RegulationsDTO;
import com.project.hrm.entities.Regulations;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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
                .applicable_salary(regulations.getApplicable_salary())
                .effective_date(regulations.getEffective_date())
                .build();
    }

    //Convert DTOS to entities
    public Regulations toRegulations(RegulationsDTO dto) {
        return Regulations.builder()
                .id(dto.getId())
                .name(dto.getName())
                .amount(dto.getAmount())
                .percentage(dto.getPercentage())
                .applicable_salary(dto.getApplicable_salary())
                .effective_date(dto.getEffective_date())
                .build();
    }

    //To entities from CreateDTOs
    public Regulations toRegulationsFromCreateDTO(RegulationsCreateDTO createDTO) {
        return Regulations.builder()
                .name(createDTO.getName())
                .amount(createDTO.getAmount())
                .percentage(createDTO.getPercentage())
                .applicable_salary(createDTO.getApplicable_salary())
                .effective_date(createDTO.getEffective_date())
                .build();
    }

    public List<RegulationsDTO> convertPageEntityToPageDTO(Page<Regulations> pageRegulations) {
        return pageRegulations.getContent()
                .stream()
                .map(this::toRegulationsDTO)
                .collect(Collectors.toList());
    }
}
