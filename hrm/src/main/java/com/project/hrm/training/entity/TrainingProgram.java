package com.project.hrm.training.entity;

import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
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
    private Role targetRole;

    @ManyToOne
    @JoinColumn
    private Employees createBy;
}