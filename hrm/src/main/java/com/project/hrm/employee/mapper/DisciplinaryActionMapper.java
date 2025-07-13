package com.project.hrm.employee.mapper;

import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionCreateDTO;
import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionDTO;
import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionUpdateDTO;
import com.project.hrm.employee.entity.DisciplinaryAction;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DisciplinaryActionMapper {

    private final Validator validator;

    /**
     * Converts DisciplinaryActionDTO to DisciplinaryAction entity.
     * Note: Employee and Regulation entities must be set separately.
     */
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

    /**
     * Converts DisciplinaryActionUpdateDTO to DisciplinaryAction entity.
     * Note: Employee and Regulation entities must be set separately.
     */
    public DisciplinaryAction toEntityFromUpdateDTO(DisciplinaryActionUpdateDTO dto) {

        return DisciplinaryAction.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .description(dto.getDescription())
                .penaltyAmount(dto.getPenaltyAmount())
                .resolved(dto.getResolved())
                .severity(dto.getSeverity())
                .build();
    }

    /**
     * Converts DisciplinaryActionCreateDTO to DisciplinaryAction entity.
     * Note: Employee and Regulation entities must be set separately.
     */
    public DisciplinaryAction toEntityFromCreateDTO(DisciplinaryActionCreateDTO dto) {

        return DisciplinaryAction.builder()
                .date(dto.getDate())
                .description(dto.getDescription())
                .penaltyAmount(dto.getPenaltyAmount())
                .resolved(dto.getResolved())
                .severity(dto.getSeverity())
                .build();
    }

    /**
     * Converts DisciplinaryAction entity to DisciplinaryActionDTO.
     */
    public DisciplinaryActionDTO toDTO(DisciplinaryAction entity) {
        if (entity == null) {
            throw new IllegalArgumentException("DisciplinaryAction entity cannot be null");
        }

        return DisciplinaryActionDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .description(entity.getDescription() != null ? entity.getDescription() : "Not Disciplinary Action")
                .employeeId(entity.getEmployee() != null ? entity.getEmployee().getId() : null)
                .regulationId(entity.getRegulation() != null ? entity.getRegulation().getId() : null)
                .penaltyAmount(entity.getPenaltyAmount() != null ? entity.getPenaltyAmount() : null)
                .resolved(entity.getResolved() != null ? entity.getResolved() : false)
                .severity(entity.getSeverity())
                .build();
    }

}