package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.hrm.enums.PayPeriodStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PayPeriods {
    @Id
    private Integer id;

    private String payPeriodCode;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayPeriodStatus status;

    @OneToMany(mappedBy = "payPeriod")
    @JsonManagedReference
    private List<Payrolls> payrolls;

}