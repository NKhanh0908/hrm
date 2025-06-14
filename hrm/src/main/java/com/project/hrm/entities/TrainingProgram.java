package com.project.hrm.entities;

import com.project.hrm.enums.TrainingStatus;
import com.project.hrm.enums.TrainingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrainingProgram {
    @Id
    private Integer id;
    private String title;
    private String description;
    private Integer durationHours;
    private Double cost;
    private LocalDate createAt;
    private String location;
    private String materials;

    @Enumerated(EnumType.STRING)
    private TrainingStatus trainingStatus;

    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    @ManyToOne
    @JoinColumn
    private Departments departments;

    @ManyToOne
    @JoinColumn
    private Employees createBy;
}
