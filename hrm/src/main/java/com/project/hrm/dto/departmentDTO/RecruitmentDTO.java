package com.project.hrm.dto.departmentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentDTO {
    private Integer id;
    private String position;
    private String contactPhone;
    private String email;
    private LocalDateTime deadline;
    private String jobDescription;
    private LocalDateTime createAt;
    private RecruitmentRequirementsDTO recruitmentRequirementsDTO;
    private List<ApplyDTO> applyDTOList;
}
