package com.project.hrm.performentEmployee.entity;


import com.project.hrm.employee.entity.Employees;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
