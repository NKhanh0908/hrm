package com.project.hrm.recruitment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CandidateProfile {

    @Id
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String linkCV;
    private String skills;
    private String experience;
    private LocalDateTime createProfileAt;

}
