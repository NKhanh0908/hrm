package com.project.hrm.payrollManager.dto.payrollsDTO;

import com.project.hrm.payrollManager.enums.PayrollStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayrollsDTO {
    private Integer id;
    private Integer employeeId;
    private Integer payPeriodId;
    private PayrollStatus status;
}
