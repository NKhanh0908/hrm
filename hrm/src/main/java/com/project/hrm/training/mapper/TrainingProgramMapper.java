package com.project.hrm.training.mapper;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramCreateDTO;
import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramDTO;
import com.project.hrm.training.entity.TrainingProgram;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingProgramMapper {
    public TrainingProgram convertCreateDTOToEntity(TrainingProgramCreateDTO trainingProgramCreateDTO){
        return TrainingProgram.builder()
                .title(trainingProgramCreateDTO.getTitle())
                .description(trainingProgramCreateDTO.getDescription())
                .createAt(LocalDateTime.now())
                .materials(trainingProgramCreateDTO.getMaterials())
                .prerequisites(trainingProgramCreateDTO.getPrerequisites())
                .isMandatory(trainingProgramCreateDTO.getIsMandatory())
                .build();
    }

    public TrainingProgramDTO convertToDTO(TrainingProgram trainingProgram){
        return TrainingProgramDTO.builder()
                .id(trainingProgram.getId())
                .title(trainingProgram.getTitle())
                .description(trainingProgram.getDescription())
                .createAt(trainingProgram.getCreateAt())
                .materials(trainingProgram.getMaterials())
                .prerequisites(trainingProgram.getPrerequisites())
                .isMandatory(trainingProgram.getIsMandatory())
                .departmentId(trainingProgram.getTargetRole().getDepartments().getId())
                .departmentName(trainingProgram.getTargetRole().getDepartments().getDepartmentName())
                .roleName(trainingProgram.getTargetRole().getName())
                .employeeId(trainingProgram.getCreateBy().getId())
                .employeeName(trainingProgram.getCreateBy().fullName())
                .build();
    }

    public List<TrainingProgramDTO> convertPageToListDTO(Page<TrainingProgram> trainingProgramPage){
        return trainingProgramPage.getContent()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<TrainingProgramDTO> toTrainingProgramPageDTO(Page<TrainingProgram> page) {
        return PageDTO.<TrainingProgramDTO>builder()
                .content(page.getContent()
                        .stream()
                        .map(this::convertToDTO)
                        .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }



}
