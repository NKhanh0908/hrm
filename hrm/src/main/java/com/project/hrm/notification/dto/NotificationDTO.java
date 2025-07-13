package com.project.hrm.notification.dto;

import com.project.hrm.notification.enums.NotificationType;
import com.project.hrm.notification.enums.SenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Integer id;
    private String title;
    private String message;
    private String senderName;
    private Integer senderId;
    private Integer recipientId;
    private NotificationType notificationType;
    private SenderType senderType;
    private boolean isRead;
    private LocalDateTime createdAt;
    private Integer referenceId;
    private String module;
    private String url;
    private String metadata;
}
