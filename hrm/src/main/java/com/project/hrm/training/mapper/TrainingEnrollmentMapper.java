package com.project.hrm.training.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.training.dto.trainingEnrollmentDTO.TrainingEnrollmentCreateDTO;
import com.project.hrm.training.dto.trainingEnrollmentDTO.TrainingEnrollmentDTO;
import com.project.hrm.training.entity.TrainingEnrollment;
import com.project.hrm.training.enums.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingEnrollmentMapper {
    public TrainingEnrollment convertCreateDTOToEntity(TrainingEnrollmentCreateDTO trainingEnrollmentCreateDTO){
        return TrainingEnrollment.builder()
                .enrollmentDate(LocalDateTime.now())
                .completionDate(null)
                .attendanceRate(trainingEnrollmentCreateDTO.getAttendanceRate() != null ? trainingEnrollmentCreateDTO.getAttendanceRate() : 0.0)
                .testScore(trainingEnrollmentCreateDTO.getTestScore() != null ? trainingEnrollmentCreateDTO.getTestScore() : 0.0)
                .feedback(trainingEnrollmentCreateDTO.getFeedback() != null ? trainingEnrollmentCreateDTO.getFeedback() : null)
                .status(EnrollmentStatus.ENROLLED)
                .build();
    }

    public TrainingEnrollmentDTO convertEntityToDTO(TrainingEnrollment trainingEnrollment){
        return TrainingEnrollmentDTO.builder()
                .id(trainingEnrollment.getId())
                .enrollmentDate(LocalDateTime.now())
                .completionDate(trainingEnrollment.getCompletionDate() == null ? null : trainingEnrollment.getCompletionDate())
                .attendanceRate(trainingEnrollment.getAttendanceRate())
                .testScore(trainingEnrollment.getTestScore())
                .feedback(trainingEnrollment.getFeedback())
                .status(trainingEnrollment.getStatus().name())
                .trainingSessionId(trainingEnrollment.getTrainingSession().getId())
                .trainingSessionName(trainingEnrollment.getTrainingSession().getSessionName())
                .employeeId(trainingEnrollment.getEmployee().getId())
                .employeeName(trainingEnrollment.getEmployee().fullName())
                .build();
    }

    public List<TrainingEnrollmentDTO> convertPageToListDTO(Page<TrainingEnrollment> trainingEnrollmentPage){
        return trainingEnrollmentPage.getContent()
                .stream().map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<TrainingEnrollmentDTO> toTrainingEnrollmentPageDTO(Page<TrainingEnrollment> page) {
        return PageDTO.<TrainingEnrollmentDTO>builder()
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
