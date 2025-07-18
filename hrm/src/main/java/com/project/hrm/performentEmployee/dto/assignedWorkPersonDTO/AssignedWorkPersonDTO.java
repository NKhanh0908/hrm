package com.project.hrm.performentEmployee.dto.assignedWorkPersonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignedWorkPersonDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime targetDate;
    private LocalDateTime completedDate;
    private Integer progressPercentage;
    private String progressNotes;
    private Integer employeeId;
    private String employeeName;
    private Integer assignedById;
    private String assignedByName;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
