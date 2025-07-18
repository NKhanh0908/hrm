package com.project.hrm.employee.dto.contractDTO;

import com.project.hrm.employee.enums.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractFilter {
    private Integer employeeId;
    private Integer departmentId;
    private Integer roleId;
    private String title;
    private LocalDateTime contractSigningDateFrom;
    private LocalDateTime contractSigningDateTo;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private ContractStatus status;
}
