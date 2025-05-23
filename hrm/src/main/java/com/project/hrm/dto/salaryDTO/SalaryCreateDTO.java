package com.project.hrm.dto.salaryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryCreateDTO {
    private LocalDateTime time;
    private Integer employeeId;
}
