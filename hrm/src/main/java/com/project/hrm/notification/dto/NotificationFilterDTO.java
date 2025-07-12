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
public class NotificationFilterDTO {
    private Boolean isRead;
    private NotificationType notificationType;
    private SenderType senderType;
    private String module;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
