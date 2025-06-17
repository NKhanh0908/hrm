package com.project.hrm.mapper;

import com.project.hrm.dto.trainingProgramDTO.TrainingProgramCreateDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramDTO;
import com.project.hrm.entities.TrainingProgram;
import com.project.hrm.enums.TrainingStatus;
import com.project.hrm.enums.TrainingType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
                .departmentId(trainingProgram.getDepartments().getId())
                .departmentName(trainingProgram.getDepartments().getDepartmentName())
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


}
