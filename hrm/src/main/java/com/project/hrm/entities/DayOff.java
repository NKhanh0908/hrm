package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Integer id;

    private LocalDateTime requestDay;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reason;
    private String status;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Employees employee;

}
