package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class PayrollDetails {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Payrolls payroll;

    @ManyToOne
    @JoinColumn
    private PayrollComponents payrollComponent;

    private BigDecimal amount;
    private Boolean isPercentage;
    private Float percentage;
}
