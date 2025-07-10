package com.project.hrm.training.mapper;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestCreateDTO;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestDTO;
import com.project.hrm.training.entity.TrainingRequest;
import com.project.hrm.training.enums.TrainingRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingRequestMapper {
    public TrainingRequest convertCreateDTOToEntity(TrainingRequestCreateDTO trainingRequestCreateDTO){
        return TrainingRequest.builder()
                .reason(trainingRequestCreateDTO.getReason())
                .expectedOutcome(trainingRequestCreateDTO.getExpectedOutcome())
                .requestDate(LocalDateTime.now())
                .approvedDate(null)
                .priority(trainingRequestCreateDTO.getPriority())
                .status(TrainingRequestStatus.PENDING)
                .build();
    }

    public TrainingRequestDTO convertEntityToDTO(TrainingRequest trainingRequest){
        return TrainingRequestDTO.builder()
                .id(trainingRequest.getId())
                .reason(trainingRequest.getReason())
                .expectedOutcome(trainingRequest.getExpectedOutcome())
                .requestDate(trainingRequest.getRequestDate())
                .approvedDate(trainingRequest.getApprovedDate() == null ? null : trainingRequest.getApprovedDate())
                .priority(trainingRequest.getPriority())
                .status(trainingRequest.getStatus())
                .targetEmployeeId(trainingRequest.getTargetEmployee().getId())
                .targetEmployeeName(trainingRequest.getTargetEmployee().fullName())
                .employeeRequestId(trainingRequest.getRequestedBy().getId())
                .employeeRequestName(trainingRequest.getRequestedBy().fullName())
                .employeeApprovedId(trainingRequest.getApprovedBy() == null ? null : trainingRequest.getApprovedBy().getId())
                .employeeApprovedName(trainingRequest.getApprovedBy() == null ? null : trainingRequest.getApprovedBy().fullName())
                .requestedProgramId(trainingRequest.getRequestedProgram().getId())
                .requestedProgramName(trainingRequest.getRequestedProgram().getTitle())
                .build();
    }

    public List<TrainingRequestDTO> convertPageToListDTO(Page<TrainingRequest> trainingRequestPage){
        return trainingRequestPage.getContent()
                .stream().map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<TrainingRequestDTO> toTrainingRequestPageDTO(Page<TrainingRequest> page) {
        return PageDTO.<TrainingRequestDTO>builder()
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
