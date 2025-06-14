package com.project.hrm.dto.trainingProgramDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramCreateDTO {
    private String title;
    private String description;
    private Integer durationHours;
    private Double cost;
    private String location;
    private String materials;
    private String trainingType;
    private Integer departmentId;
}
