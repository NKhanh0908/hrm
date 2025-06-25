package com.project.hrm.mapper;

import com.project.hrm.dto.dayOffDTO.DayOffCreateDTO;
import com.project.hrm.dto.dayOffDTO.DayOffDTO;
import com.project.hrm.entities.DayOff;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DayOffMapper {

    public DayOff toEntity(DayOffDTO dayOffDTO) {
        return DayOff.builder()
                .id(dayOffDTO.getId())
                .requestDay(dayOffDTO.getRequestDay())
                .startDate(dayOffDTO.getStartDate())
                .endDate(dayOffDTO.getEndDate())
                .reason(dayOffDTO.getReason())
                .status(dayOffDTO.getStatus())
                .build();
    }

    public DayOffDTO toDTO(DayOff dayOff) {
        return DayOffDTO.builder()
                .id(dayOff.getId())
                .requestDay(dayOff.getRequestDay())
                .startDate(dayOff.getStartDate())
                .endDate(dayOff.getEndDate())
                .reason(dayOff.getReason())
                .status(dayOff.getStatus())
                .employeeId(dayOff.getEmployee().getId())
                .build();
    }

    public DayOff toEntityFromDTO(DayOffCreateDTO dayOffCreateDTO) {
        return DayOff.builder()
                .requestDay(dayOffCreateDTO.getRequestDay())
                .startDate(dayOffCreateDTO.getStartDate())
                .endDate(dayOffCreateDTO.getEndDate())
                .reason(dayOffCreateDTO.getReason())
                .status(dayOffCreateDTO.getStatus())
                .build();
    }
}
