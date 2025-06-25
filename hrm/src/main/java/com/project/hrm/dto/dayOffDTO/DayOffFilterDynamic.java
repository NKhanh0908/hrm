package com.project.hrm.dto.dayOffDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayOffFilterDynamic {
    private LocalDateTime requestDayFrom;
    private LocalDateTime requestDayTo;
    private LocalDateTime startDateFrom;
    private LocalDateTime endDateTo;
    private String status;
    private Boolean useRequestDayFilter;
    private Boolean useStartEndOverlapFilter;
}
