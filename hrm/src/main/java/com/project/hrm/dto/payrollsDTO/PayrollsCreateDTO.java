package com.project.hrm.dto.payrollsDTO;

import com.project.hrm.enums.PayrollStatus;
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
public class PayrollsCreateDTO {
    private Integer employeeId;
    private PayrollStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
