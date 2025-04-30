package com.project.hrm.dto.departmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyUpdateDTO {
    private Integer id;
    private LocalDateTime applyAt;
    private String status;
    private String position;
    private Integer recruitmentId;
    private Integer candidateProfileId;
}
