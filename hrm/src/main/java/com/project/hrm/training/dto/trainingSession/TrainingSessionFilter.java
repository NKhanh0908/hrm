package com.project.hrm.training.dto.trainingSession;

import lombok.Data;

@Data
public class TrainingSessionFilter {
    private String sessionName;
    private Integer trainingProgramId;
    private Integer coordinatorId;
}
