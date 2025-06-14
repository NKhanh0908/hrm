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
public class TrainingProgramDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer durationHours;
    private Double cost;
    private LocalDate createAt;
    private String location;
    private String materials;
    private String trainingStatus;
    private String trainingType;
    private Integer departmentId;
    private String departmentName;
    private Integer employeeId;
    private String employeeName;
}
