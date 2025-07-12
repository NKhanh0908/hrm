package com.project.hrm.training.dto.trainingProgramDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramUpdateDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private String materials;
    private String prerequisites;
    private Boolean isMandatory;
}
