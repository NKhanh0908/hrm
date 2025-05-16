package com.project.hrm.dto.candidateProfileDTO;



import lombok.Data;

@Data
public class CandidateProfileFilter {
    private String name;
    private String email;
    private String phone;
    private String position;       // Apply.position
    private Integer recruitmentId; // Apply.recruitment.id
}

