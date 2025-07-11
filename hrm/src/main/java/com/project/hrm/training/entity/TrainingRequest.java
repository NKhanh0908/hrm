package com.project.hrm.training.entity;

import com.project.hrm.employee.entity.Employees;
import com.project.hrm.training.enums.TrainingRequestStatus;
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
public class TrainingRequest {
    @Id
    private Integer id;
    private String reason;
    private String expectedOutcome;
    private LocalDateTime requestDate;
    private LocalDateTime approvedDate;
    private String priority;

    @Enumerated(EnumType.STRING)
    private TrainingRequestStatus status;

    @ManyToOne
    @JoinColumn
    private Employees targetEmployee;

    @ManyToOne
    @JoinColumn
    private Employees requestedBy;

    @ManyToOne
    @JoinColumn
    private Employees approvedBy;

    @ManyToOne
    @JoinColumn
    private TrainingProgram requestedProgram;

}