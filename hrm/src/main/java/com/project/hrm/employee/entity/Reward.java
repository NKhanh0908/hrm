package com.project.hrm.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reward{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String reason;

    private BigDecimal rewardAmount;

    private Boolean isPercentage;
    private Float percentage;

    private LocalDateTime rewardDate;

    private Boolean appliedToPayroll = false;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employees employee;
}
