package com.project.hrm.training.dto.trainingEnrollmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingEnrollmentUpdateDTO {
    private Integer id;
    private LocalDateTime enrollmentDate;
    private LocalDateTime completionDate;
    private Double attendanceRate;
    private Double testScore;
    private String feedback;
    private String status;
}
