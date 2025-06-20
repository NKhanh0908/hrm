package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.enums.AttendenceType;
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
public class Attendence {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Employees employee;

    private LocalDateTime attendenceDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Float regularTime;
    private Float otherTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendenceType shiftType;
}
