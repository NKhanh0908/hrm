package com.project.hrm.dto.assignmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentFilter {
    private Integer employeeId;
    private Integer departmentId;
    private Integer roleId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
