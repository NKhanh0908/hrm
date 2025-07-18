package com.project.hrm.recruitment.dto.evaluateDTO;

import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileDTO;
import com.project.hrm.employee.dto.employeeDTO.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateDTO {
    private Integer id;
    private String feedback;
    private LocalDateTime feedbackAt;
    private String evaluate;
    private EmployeeDTO employeeDTO;
    private CandidateProfileDTO candidateProfileDTO;
}
