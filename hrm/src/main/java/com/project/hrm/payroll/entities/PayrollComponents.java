package com.project.hrm.payroll.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.payroll.enums.PayrollComponentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PayrollComponents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayrollComponentType type;
    private BigDecimal amount;

    private Boolean isPercentage;
    private Float percentage;

    @ManyToOne
    @JoinColumn
    @JsonBackReference("regulation-payrollComponents")
    private Regulations regulation;

    @ManyToOne
    @JoinColumn
    @JsonBackReference("payroll-payrollComponent")
    private Payrolls payroll;
}
