package com.project.hrm.entities;


import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Evaluate {
    @Id
    private Integer id = IdGenerator.getGenerationId();
    private String feedback;
    private LocalDateTime feedbackAt;
    private String evaluate;

    @ManyToOne
    @JoinColumn
    private CandidateProfile candidateProfile;

    @ManyToOne
    @JoinColumn
    private Employees createBy;

    public Evaluate(Evaluate evaluate) {
        this.id = IdGenerator.getGenerationId();
        this.feedback = evaluate.getFeedback();
        this.feedbackAt = LocalDateTime.now();
        this.evaluate = evaluate.getEvaluate();
        this.candidateProfile = evaluate.getCandidateProfile();
        this.createBy = evaluate.getCreateBy();
    }
}