package com.project.hrm.recruitment.dto.applyDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyFilter {
    private LocalDateTime applyAt;
    private String status;
    private String position;
    private Integer recruitmentID;
}
