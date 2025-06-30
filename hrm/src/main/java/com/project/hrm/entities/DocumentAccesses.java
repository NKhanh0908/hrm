package com.project.hrm.entities;

import com.project.hrm.enums.DocumentAccess;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentAccesses {
    @Id
    private Integer id;
    private LocalDateTime accessedAt;

    @ManyToOne
    @JoinColumn
    private Documents documents;

    @ManyToOne
    @JoinColumn
    private Employees employees;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentAccess accessLevel;

}
