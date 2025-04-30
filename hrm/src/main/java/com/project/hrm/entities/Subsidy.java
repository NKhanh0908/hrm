package com.project.hrm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subsidy {
    @Id
    private Integer id;
    private String typeSubsidy;
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Salary salary;
}
