package com.project.hrm.payroll.dto.payrollsDTO;

import com.project.hrm.payroll.enums.PayrollStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayrollsUpdateDTO {
    private Integer id;
    private Integer employeeId;
    private Integer payPeriodId;
    private PayrollStatus status;
}
