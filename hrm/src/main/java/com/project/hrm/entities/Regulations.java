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

    private String name; //Tên của luật
    private BigDecimal amount; //Mức tiền trong quy định, có thể NULL
    private Float percentage; //Phần trăm trong quy định, có thể NULL
    private BigDecimal applicable_salary; //Mức lương áp dụng
    private LocalDateTime effective_date; //Ngày áp dụng

    @OneToMany(mappedBy = "regulation")
    @JsonManagedReference
    private List<PayrollComponents> payrollComponents;
}
