package com.project.hrm.mapper;

import com.project.hrm.dto.trainingSession.TrainingSessionCreateDTO;
import com.project.hrm.dto.trainingSession.TrainingSessionDTO;
import com.project.hrm.entities.TrainingSession;
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
}
