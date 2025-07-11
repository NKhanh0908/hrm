package com.project.hrm.training.entity;

import com.project.hrm.employee.entity.Employees;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrainingSession {
    @Id
    private Integer id;
    private String sessionName;
    private Integer durationHours;
    private Double cost;
    private String location;
    private Integer maxParticipants;
    private Integer currentParticipants;

    @ManyToOne
    @JoinColumn
    private TrainingProgram trainingProgram;

    @ManyToOne
    @JoinColumn(name = "coordinator")
    private Employees coordinator;
}