package com.project.hrm.dto.trainingProgramDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramCreateDTO {
    private String title;
    private String description;
    private LocalDateTime createAt;
    private String materials;
    private String prerequisites;
    private Boolean isMandatory;
    private Integer roleId;
}
