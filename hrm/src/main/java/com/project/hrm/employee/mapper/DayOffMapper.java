package com.project.hrm.employee.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.dayOffDTO.DayOffCreateDTO;
import com.project.hrm.employee.dto.dayOffDTO.DayOffDTO;
import com.project.hrm.employee.entity.DayOff;
import com.project.hrm.employee.enums.DayOffStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
                .status(DayOffStatus.valueOf(dayOffDTO.getStatus()))
                .build();
    }

    public DayOffDTO toDTO(DayOff dayOff) {
        return DayOffDTO.builder()
                .id(dayOff.getId())
                .requestDay(dayOff.getRequestDay())
                .startDate(dayOff.getStartDate())
                .endDate(dayOff.getEndDate())
                .reason(dayOff.getReason())
                .status(String.valueOf(dayOff.getStatus()))
                .employeeId(dayOff.getEmployee().getId())
                .build();
    }

    public DayOff toEntityFromDTO(DayOffCreateDTO dayOffCreateDTO) {
        return DayOff.builder()
                .requestDay(dayOffCreateDTO.getRequestDay())
                .startDate(dayOffCreateDTO.getStartDate())
                .endDate(dayOffCreateDTO.getEndDate())
                .reason(dayOffCreateDTO.getReason())
                .status(DayOffStatus.valueOf(dayOffCreateDTO.getStatus()))
                .build();
    }

    public PageDTO<DayOffDTO> toDayOffPageDTO(Page<DayOff> page) {
        return PageDTO.<DayOffDTO>builder()
                .content(
                        page.getContent()
                                .stream()
                                .map(this::toDTO)      // dùng toDTO(DayOff)
                                .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

}
