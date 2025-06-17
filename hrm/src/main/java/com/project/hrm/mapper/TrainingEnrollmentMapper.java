package com.project.hrm.mapper;

import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentCreateDTO;
import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentDTO;
import com.project.hrm.entities.TrainingEnrollment;
import com.project.hrm.enums.EnrollmentStatus;
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
                .attendanceRate(trainingEnrollmentCreateDTO.getAttendanceRate())
                .testScore(trainingEnrollmentCreateDTO.getTestScore())
                .feedback(trainingEnrollmentCreateDTO.getFeedback())
                .status(EnrollmentStatus.valueOf(trainingEnrollmentCreateDTO.getStatus()))
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
}
