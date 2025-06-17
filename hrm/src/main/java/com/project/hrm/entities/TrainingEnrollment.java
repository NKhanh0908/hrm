package com.project.hrm.entities;

import com.project.hrm.enums.EnrollmentStatus;
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
public class TrainingEnrollment {
    @Id
    private Integer id;
    private LocalDateTime enrollmentDate;
    private LocalDateTime completionDate;
    private Double attendanceRate;
    private Double testScore;
    private String feedback;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    @ManyToOne
    @JoinColumn
    private TrainingSession trainingSession;

    @ManyToOne
    @JoinColumn
    private TrainingRequest trainingRequest;

    @ManyToOne
    @JoinColumn
    private Employees employee;
}