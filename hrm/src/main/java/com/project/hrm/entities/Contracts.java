package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.enums.ContractStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "existsOverlappingActiveContract",
                procedureName = "exists_overlapping_active_contract",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "employee_id", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "role_id", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_start", type = LocalDateTime.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_end", type = LocalDateTime.class),

                }
        ),
        @NamedStoredProcedureQuery(
                name = "getTotalContractByStatusAndSalary",
                procedureName = "get_total_contract_by_status_and_salary",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "contract_status", type = String.class),
                }
        )
})

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contracts {
    @Id
    private Integer id;
    private String title;
    private LocalDateTime contractSigningDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double baseSalary;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus contractStatus;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Employees employee;

    @ManyToOne
    @JoinColumn
    private Role role;
}
