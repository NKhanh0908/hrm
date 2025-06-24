package com.project.hrm.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class AssignedWorkPerson {
    @Id
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime targetDate;
    private LocalDateTime completedDate;
    private Integer progressPercentage;
    private String progressNotes;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employees employee;

    @ManyToOne
    @JoinColumn
    private Employees assignedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
