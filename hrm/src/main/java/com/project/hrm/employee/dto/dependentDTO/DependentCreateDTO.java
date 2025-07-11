package com.project.hrm.employee.dto.dependentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DependentCreateDTO {
    private String name;
    private String relationship;
    private LocalDateTime birthDate;
    private Integer employeeId;
}
