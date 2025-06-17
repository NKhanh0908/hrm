package com.project.hrm.dto.trainingEnrollmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingEnrollmentCreateDTO {
    private Double attendanceRate;
    private Double testScore;
    private String feedback;
    private String status;
    private Integer trainingSessionId;
    private Integer trainingRequestId;
    private Integer employeeId;
}
