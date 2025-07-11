package com.project.hrm.systemRegulation.mapper;

import com.project.hrm.systemRegulation.dto.systemRegulationDTO.SystemRegulationDTO;
import com.project.hrm.systemRegulation.entity.SystemRegulation;
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
