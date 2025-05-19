package com.project.hrm.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.enums.ApplyStatus;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Apply {
    @Id
    private Integer id = IdGenerator.getGenerationId();
    private LocalDateTime applyAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplyStatus applyStatus;

    private String position;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Recruitment recruitment;
    
    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private CandidateProfile candidateProfile;

    public Apply(Apply apply){
        this.id = IdGenerator.getGenerationId();
        this.applyAt = LocalDateTime.now();
        this.applyStatus = apply.getApplyStatus();
        this.position = apply.getPosition();
        this.recruitment = apply.getRecruitment();
        this.candidateProfile = apply.getCandidateProfile();
    }
}
