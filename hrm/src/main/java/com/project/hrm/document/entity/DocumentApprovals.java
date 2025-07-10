package com.project.hrm.document.entity;

import com.project.hrm.entities.Employees;
import com.project.hrm.document.enums.DocumentApprovalsStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentApprovals {
    @Id
    private Integer id;
    private LocalDateTime approvalDate;
    private String reason;
    private LocalDateTime requestedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentApprovalsStatus documentApprovalsStatus;

    @ManyToOne
    @JoinColumn
    private Documents documents;

    @ManyToOne
    @JoinColumn
    private Employees approvedBy;

    @ManyToOne
    @JoinColumn
    private Employees requestedBy;
}
