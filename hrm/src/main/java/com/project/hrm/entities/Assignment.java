package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.utils.IdGenerator;
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
public class Assignment {
    @Id
    private Integer id = IdGenerator.getGenerationId();

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Employees employees;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Departments departments;

    @ManyToOne
    @JoinColumn
    private Role role;

    private LocalDateTime startDate = LocalDateTime.now();

    private LocalDateTime endDate;

}

