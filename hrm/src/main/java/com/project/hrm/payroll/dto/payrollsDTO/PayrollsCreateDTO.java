package com.project.hrm.payroll.dto.payrollsDTO;

import com.project.hrm.payroll.enums.PayrollStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
