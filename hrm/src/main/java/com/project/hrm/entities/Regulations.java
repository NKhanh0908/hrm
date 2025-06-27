package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private Integer id;

    private String regulationKey;
    private String name;
    private BigDecimal amount;
    private Float percentage;
    private BigDecimal applicableSalary;
    private LocalDateTime effectiveDate;

    @OneToMany(mappedBy = "regulation")
    @JsonManagedReference("regulation-payrollComponents")
    private List<PayrollComponents> payrollComponents;
}
