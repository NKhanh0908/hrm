package com.project.hrm.dto.trainingSession;

import lombok.Data;

@Data
public class TrainingSessionFilter {
    private String sessionName;
    private Integer trainingProgramId;
    private Integer coordinatorId;
}
