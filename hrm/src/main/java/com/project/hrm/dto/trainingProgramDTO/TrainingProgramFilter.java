package com.project.hrm.dto.trainingProgramDTO;

import com.project.hrm.enums.TrainingStatus;
import com.project.hrm.enums.TrainingType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingProgramFilter {
    private String title;
    private Integer durationMin;
    private Integer durationMax;
    private Double costMin;
    private Double costMax;
    private LocalDate createdFrom;
    private LocalDate createdTo;
    private TrainingStatus trainingStatus;
    private TrainingType trainingType;
    private Integer departmentId;
    private Integer createdById;
}