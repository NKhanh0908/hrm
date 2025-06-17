package com.project.hrm.dto.trainingSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingSessionDTO {
    private Integer id;
    private String sessionName;
    private Integer durationHours;
    private Double cost;
    private String location;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private Integer trainingProgramId;
    private Integer coordinatorEmployeeId;
    private String coordinatorEmployeeName;
    private String coordinatorEmployeeEmail;
    private String coordinatorEmployeePhone;
}
