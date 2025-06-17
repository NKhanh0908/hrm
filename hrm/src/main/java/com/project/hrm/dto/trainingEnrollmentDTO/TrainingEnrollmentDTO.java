package com.project.hrm.dto.trainingEnrollmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingEnrollmentDTO {
    private Integer id;
    private LocalDateTime enrollmentDate;
    private LocalDateTime completionDate;
    private Double attendanceRate;
    private Double testScore;
    private String feedback;
    private String status;
    private Integer trainingSessionId;
    private String trainingSessionName;
    private Integer employeeId;
    private String employeeName;
}
