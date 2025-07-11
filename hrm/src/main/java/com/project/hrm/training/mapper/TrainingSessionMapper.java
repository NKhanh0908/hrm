package com.project.hrm.training.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionCreateDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionDTO;
import com.project.hrm.training.entity.TrainingSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingSessionMapper {
    public TrainingSession convertCreateDTOToEntity(TrainingSessionCreateDTO trainingSessionCreateDTO){
        return TrainingSession.builder()
                .sessionName(trainingSessionCreateDTO.getSessionName())
                .durationHours(trainingSessionCreateDTO.getDurationHours())
                .cost(trainingSessionCreateDTO.getCost())
                .location(trainingSessionCreateDTO.getLocation())
                .maxParticipants(trainingSessionCreateDTO.getMaxParticipants())
                .currentParticipants(trainingSessionCreateDTO.getCurrentParticipants())
                .build();
    }

    public TrainingSessionDTO convertEntityToDTO(TrainingSession trainingSession){
        return TrainingSessionDTO.builder()
                .id(trainingSession.getId())
                .sessionName(trainingSession.getSessionName())
                .durationHours(trainingSession.getDurationHours())
                .cost(trainingSession.getCost())
                .location(trainingSession.getLocation())
                .maxParticipants(trainingSession.getMaxParticipants())
                .currentParticipants(trainingSession.getCurrentParticipants())
                .trainingProgramId(trainingSession.getTrainingProgram().getId())
                .coordinatorEmployeeId(trainingSession.getCoordinator().getId())
                .coordinatorEmployeeName(trainingSession.getCoordinator().fullName())
                .coordinatorEmployeeEmail(trainingSession.getCoordinator().getEmail())
                .coordinatorEmployeePhone(trainingSession.getCoordinator().getPhone())
                .build();
    }

    public List<TrainingSessionDTO> convertListEntityToListDTO(List<TrainingSession> trainingSessionPage){
        return trainingSessionPage
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<TrainingSessionDTO> toTrainingSessionPageDTO(Page<TrainingSession> trainingSessionPage){
        return PageDTO.<TrainingSessionDTO>builder()
                        .content(trainingSessionPage.getContent()
                                .stream()
                                .map(this::convertEntityToDTO)
                                .collect(Collectors.toList()))
                .page(trainingSessionPage.getNumber())
                .size(trainingSessionPage.getSize())
                .totalElements(trainingSessionPage.getTotalElements())
                .totalPages(trainingSessionPage.getTotalPages())
                .build();
    }
}
