package com.project.hrm.payroll.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.hrm.payroll.enums.PayrollComponentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Regulations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String regulationKey;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayrollComponentType type;

    private BigDecimal amount;
    private Float percentage;
    private BigDecimal applicableSalary;
    private LocalDateTime effectiveDate;

    @OneToMany(mappedBy = "regulation")
    @JsonManagedReference("regulation-payrollComponents")
    private List<PayrollComponents> payrollComponents;
}
