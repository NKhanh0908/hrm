package com.project.hrm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Deduction {
    @Id
    private Integer id;
    private String typeDeduction;
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Salary salary;
}
