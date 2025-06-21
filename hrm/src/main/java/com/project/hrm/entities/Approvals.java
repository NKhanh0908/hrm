package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.enums.PayrollStatus;
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
public class Approvals {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Employees employeeReview;

    @OneToOne
    @JoinColumn
    private Payrolls payroll;

    private LocalDateTime approvalDate;
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayrollStatus status;
}
