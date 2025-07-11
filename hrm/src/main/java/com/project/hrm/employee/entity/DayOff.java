package com.project.hrm.employee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.employee.enums.DayOffStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DayOff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime requestDay;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOffStatus status;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Employees employee;

}
