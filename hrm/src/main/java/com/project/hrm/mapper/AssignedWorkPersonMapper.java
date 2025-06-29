package com.project.hrm.mapper;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonCreateDTO;
import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonDTO;
import com.project.hrm.entities.AssignedWorkPerson;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssignedWorkPersonMapper {
    public AssignedWorkPerson convertCreateDTOToEntity(AssignedWorkPersonCreateDTO assignedWorkPersonCreateDTO){
        return AssignedWorkPerson.builder()
                .title(assignedWorkPersonCreateDTO.getTitle())
                .description(assignedWorkPersonCreateDTO.getDescription())
                .startDate(assignedWorkPersonCreateDTO.getStartDate())
                .targetDate(assignedWorkPersonCreateDTO.getTargetDate())
                .progressPercentage(0)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public AssignedWorkPersonDTO convertEntityToDTO(AssignedWorkPerson assignedWorkPerson){
        return AssignedWorkPersonDTO.builder()
                .id(assignedWorkPerson.getId())
                .title(assignedWorkPerson.getTitle())
                .description(assignedWorkPerson.getDescription())
                .startDate(assignedWorkPerson.getStartDate())
                .targetDate(assignedWorkPerson.getTargetDate())
                .progressPercentage(assignedWorkPerson.getProgressPercentage())
                .progressNotes(assignedWorkPerson.getProgressNotes()!= null ? assignedWorkPerson.getProgressNotes(): null)
                .employeeId(assignedWorkPerson.getEmployee().getId())
                .employeeName(assignedWorkPerson.getEmployee().fullName())
                .assignedById(assignedWorkPerson.getAssignedBy().getId())
                .assignedByName(assignedWorkPerson.getAssignedBy().fullName())
                .createAt(assignedWorkPerson.getCreatedAt())
                .updateAt(assignedWorkPerson.getUpdatedAt() != null ? assignedWorkPerson.getUpdatedAt() : null)
                .build();
    }

    public List<AssignedWorkPersonDTO> convertEntityListToDTOList(List<AssignedWorkPerson> assignedWorkPersonList){
        return assignedWorkPersonList.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<AssignedWorkPersonDTO> toAssignedWorkPersonPageDTO(Page<AssignedWorkPerson> page) {
        return PageDTO.<AssignedWorkPersonDTO>builder()
                .content(page.getContent()
                        .stream()
                        .map(this::convertEntityToDTO)
                        .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
