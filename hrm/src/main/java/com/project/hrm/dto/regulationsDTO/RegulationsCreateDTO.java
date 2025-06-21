package com.project.hrm.dto.regulationsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegulationsCreateDTO {
    private String name; //Tên của luật
    private BigDecimal amount; //Mức tiền trong quy định, có thể NULL
    private Float percentage; //Phần trăm trong quy định, có thể NULL
    private BigDecimal applicable_salary; //Mức lương áp dụng
    private LocalDateTime effective_date;
}
