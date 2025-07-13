package com.project.hrm.notification.dto;

import com.project.hrm.employee.entity.Employees;
import com.project.hrm.notification.enums.NotificationType;
import com.project.hrm.notification.enums.SenderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCreateDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @NotBlank(message = "Message is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String message;

    @NotNull(message = "Sender ID is required")
    private Integer sender;

    @NotNull(message = "Sender type is required")
    private SenderType senderType;

    @NotNull(message = "Recipient ID is required")
    private Integer recipient;

    @NotNull(message = "Notification type is required")
    private String notificationType;

    private String module;

    private Integer referenceId;

    private String metadata;
}
