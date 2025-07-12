package com.project.hrm.training.dto.trainingProgramDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Training program ID is required")
    private Integer id;

    @NotBlank(message = "Training title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Size(max = 1000, message = "Materials must not exceed 1000 characters")
    private String materials;

    @Size(max = 1000, message = "Prerequisites must not exceed 1000 characters")
    private String prerequisites;

    private Boolean isMandatory = false;
}
