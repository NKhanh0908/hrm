package com.project.hrm.employee.dto.dayOffDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayOffUpdateDTO {

    @NotNull(message = "Day-off request ID is required")
    private Integer id;

    @NotNull(message = "Request day is required")
    private LocalDateTime requestDay;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    @Size(max = 1000, message = "Reason must not exceed 1000 characters")
    private String reason;

    @NotNull(message = "Status is required")
    @Pattern(
            regexp = "PENDING|APPROVED",
            message = "Status must be one of: PENDING, APPROVED"
    )
    private String status;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;
}
