package com.project.hrm.document.entity;

import com.project.hrm.employee.entity.Employees;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Documents documents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Employees approvedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Employees requestedBy;
}
