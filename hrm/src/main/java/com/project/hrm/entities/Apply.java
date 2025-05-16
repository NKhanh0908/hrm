package com.project.hrm.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Integer id;
    private LocalDateTime applyAt;
    private String status;
    private String position;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Recruitment recruitment;
    
    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private CandidateProfile candidateProfile;

}
