package com.project.hrm.mapper;

import com.project.hrm.dto.systemRegulationDTO.SystemRegulationDTO;
import com.project.hrm.entities.SystemRegulation;
import org.springframework.stereotype.Component;

@Component
public class SystemRegulationMapper {

    public SystemRegulation toEntity(SystemRegulationDTO sysRegulationDTO) {
        return SystemRegulation.builder()
                .key(sysRegulationDTO.getKey())
                .value(sysRegulationDTO.getValue())
                .description(sysRegulationDTO.getDescription())
                .build();
    }

    public SystemRegulationDTO toDTO(SystemRegulation sysRegulation) {
        return SystemRegulationDTO.builder()
                .key(sysRegulation.getKey())
                .value(sysRegulation.getValue())
                .description(sysRegulation.getDescription())
                .build();
    }
}
