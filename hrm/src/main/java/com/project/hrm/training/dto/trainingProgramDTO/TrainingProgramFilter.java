package com.project.hrm.training.dto.trainingProgramDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingProgramFilter {
    @Schema(description = "Title of the training program", example = "Leadership", nullable = true)
    private String title;

    @Schema(description = "Filter from created date", example = "2024-01-01", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDate createdFrom;

    @Schema(description = "Filter to created date", example = "2024-12-31", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDate createdTo;

    @Schema(description = "Is the program mandatory", example = "true", implementation = Boolean.class, nullable = true)
    private Boolean isMandatory;

    @Schema(description = "Department ID associated", example = "3", nullable = true)
    private Integer departmentId;

    @Schema(description = "Role ID associated", example = "2", nullable = true)
    private Integer roleId;

    @Schema(description = "Created by employee ID", example = "15", nullable = true)
    private Integer createdById;
}