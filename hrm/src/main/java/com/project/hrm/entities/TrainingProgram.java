package com.project.hrm.entities;

import com.project.hrm.enums.TrainingStatus;
import com.project.hrm.enums.TrainingType;
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
public class TrainingProgram {
    @Id
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private String materials;
    private String prerequisites;
    private Boolean isMandatory;

    @ManyToOne
    @JoinColumn
    private Departments departments;

    @ManyToOne
    @JoinColumn
    private Role targetRole;

    @ManyToOne
    @JoinColumn
    private Employees createBy;
}