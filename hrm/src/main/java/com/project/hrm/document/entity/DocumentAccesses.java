package com.project.hrm.document.entity;

import com.project.hrm.employee.entity.Employees;
import com.project.hrm.document.enums.DocumentAccess;
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
