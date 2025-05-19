package com.project.hrm.entities;

import com.project.hrm.utils.IdGenerator;
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
    private Integer id = IdGenerator.getGenerationId();
    private String name;
    private String email;
    private String phone;
    private String linkCV;
    private String skills;
    private String experience;
    private LocalDateTime createProfileAt;

    public CandidateProfile(CandidateProfile candidateProfile) {
        this.id = IdGenerator.getGenerationId();
        this.name = candidateProfile.getName();
        this.email = candidateProfile.getEmail();
        this.phone = candidateProfile.getPhone();
        this.linkCV = candidateProfile.getLinkCV();
        this.skills = candidateProfile.getSkills();
        this.experience = candidateProfile.getExperience();
        this.createProfileAt = LocalDateTime.now();
    }
}
