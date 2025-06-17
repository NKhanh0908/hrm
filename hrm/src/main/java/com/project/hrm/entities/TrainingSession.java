package com.project.hrm.entities;

import com.project.hrm.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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