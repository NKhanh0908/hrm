package com.project.hrm.dto.assignedWorkPersonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignedWorkPersonCreateDTO {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime targetDate;
    private LocalDateTime completedDate;
    private Integer employeeId;

}
