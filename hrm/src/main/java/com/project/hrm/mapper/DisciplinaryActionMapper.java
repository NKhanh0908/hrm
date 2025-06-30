package com.project.hrm.mapper;

import com.project.hrm.dto.disciplinaryActionDTO.DisciplinaryActionCreateDTO;
import com.project.hrm.dto.disciplinaryActionDTO.DisciplinaryActionDTO;
import com.project.hrm.entities.DisciplinaryAction;
import org.springframework.stereotype.Component;

@Component
public class DisciplinaryActionMapper {
    public DisciplinaryAction toEntity(DisciplinaryActionDTO dto) {
        return DisciplinaryAction.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .description(dto.getDescription())
                .penaltyAmount(dto.getPenaltyAmount())
                .resolved(dto.getResolved())
                .severity(dto.getSeverity())
                .build();
    }

    public DisciplinaryActionDTO toDTO(DisciplinaryAction entity) {
        return DisciplinaryActionDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .description(entity.getDescription())
                .employeeId(entity.getEmployee().getId())
                .regulationId(entity.getRegulation().getId())
                .penaltyAmount(entity.getPenaltyAmount())
                .resolved(entity.getResolved())
                .severity(entity.getSeverity())
                .build();
    }

    public DisciplinaryAction toEntityFromCreateDTO(DisciplinaryActionCreateDTO createDTO) {
        return DisciplinaryAction.builder()
                .description(createDTO.getDescription())
                .date(createDTO.getDate())
                .penaltyAmount(createDTO.getPenaltyAmount())
                .resolved(createDTO.getResolved())
                .severity(createDTO.getSeverity())
                .build();
    }
}
