package com.project.hrm.entities;

import com.project.hrm.utils.IdGenerator;

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
    private Integer id = IdGenerator.getGenerationId();
    private String typeDeduction;
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Salary salary;
}
