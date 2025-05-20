package com.project.hrm.dto.othersDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewLetter {
    private Integer applyId;
    private String address;
    private LocalDateTime interviewTime;
}
