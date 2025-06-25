package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.hrm.enums.PayrollComponentType;
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
public class PayrollComponents {
    @Id
    private Integer id;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayrollComponentType type;
    private BigDecimal amount;
    private Float percentage;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Regulations regulation;

    @OneToMany
    @JoinColumn
    @JsonManagedReference
    private List<PayrollDetails> details;
}
