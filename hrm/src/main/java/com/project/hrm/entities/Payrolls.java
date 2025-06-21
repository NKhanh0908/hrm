package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.hrm.enums.PayrollStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payrolls {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Employees employee;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private PayPeriods payPeriod;

    private BigDecimal total_income;
    private BigDecimal total_deduction;
    private BigDecimal net_salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayrollStatus status;

    @OneToMany(mappedBy = "payroll")
    @JsonManagedReference
    private List<PayrollDetails> payrollDetails;

}
