package com.project.hrm.payrollManager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.hrm.entities.Employees;
import com.project.hrm.payrollManager.enums.PayrollStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payrolls", indexes = {
    @Index(name = "idx_payrolls_employee", columnList = "employee_id"),
    @Index(name = "idx_payrolls_pay_period", columnList = "pay_period_id"),
    @Index(name = "idx_payrolls_status", columnList = "status"),
    @Index(name = "idx_payrolls_employee_period", columnList = "employee_id, pay_period_id")
})
public class Payrolls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Employees employee;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private PayPeriods payPeriod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayrollStatus status;

    @OneToMany(mappedBy = "payroll")
    @JsonManagedReference("payroll-payrollComponent")
    private List<PayrollComponents> payrollDComponents;

}
