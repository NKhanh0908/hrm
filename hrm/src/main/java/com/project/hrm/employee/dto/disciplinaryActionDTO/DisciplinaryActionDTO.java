package com.project.hrm.employee.dto.disciplinaryActionDTO;

import com.project.hrm.employee.enums.ViolationSeverity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaryActionDTO {
    private Integer id;
    private String description;
    private LocalDateTime date;
    private Integer employeeId;
    private Integer regulationId;
    private BigDecimal penaltyAmount;
    private Boolean resolved;
    private ViolationSeverity severity;
}

