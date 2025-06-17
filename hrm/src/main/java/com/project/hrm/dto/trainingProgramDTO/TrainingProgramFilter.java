package com.project.hrm.dto.trainingProgramDTO;

import com.project.hrm.enums.TrainingStatus;
import com.project.hrm.enums.TrainingType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingProgramFilter {
    private String title;
    private LocalDate createdFrom;
    private LocalDate createdTo;
    private Boolean isMandatory;
    private Integer departmentId;
    private Integer roleId;
    private Integer createdById;
}