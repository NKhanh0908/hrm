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
public class Salary {
    @Id
    private Integer id = IdGenerator.getGenerationId();
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn
    private Employees employee;
}
