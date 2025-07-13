package com.project.hrm.notification.entity;

import com.project.hrm.employee.entity.Employees;
import com.project.hrm.notification.enums.NotificationType;
import com.project.hrm.notification.enums.SenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private Integer id;
    private String title;
    private String message;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Employees recipient;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Employees senderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SenderType senderType;

    @Column(nullable = false)
    private boolean isRead;

    private LocalDateTime createdAt;

    private String module;

    private Integer referenceId;

    @Column(columnDefinition = "json")
    private String metadata;


}
