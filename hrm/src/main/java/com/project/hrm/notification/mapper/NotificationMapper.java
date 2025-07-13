package com.project.hrm.notification.mapper;

import com.project.hrm.employee.entity.Employees;
import com.project.hrm.notification.dto.NotificationCreateDTO;
import com.project.hrm.notification.dto.NotificationDTO;
import com.project.hrm.notification.entity.Notification;
import com.project.hrm.notification.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {
    public Notification covertCreateDTOToEntity(NotificationCreateDTO notificationCreateDTO) {
        return Notification.builder()
                .title(notificationCreateDTO.getTitle())
                .message(notificationCreateDTO.getMessage())
                .notificationType(NotificationType.valueOf(notificationCreateDTO.getNotificationType()))
                .senderType(notificationCreateDTO.getSenderType())
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .module(notificationCreateDTO.getModule())
                .metadata(notificationCreateDTO.getMetadata())
                .build();
    }

    public NotificationDTO covertEntityToDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .recipientId(notification.getRecipient().getId())
                .senderId(notification.getSenderId().getId())
                .notificationType(notification.getNotificationType())
                .senderType(notification.getSenderType())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .module(notification.getModule())
                .referenceId(notification.getReferenceId())
                .metadata(notification.getMetadata())
                .build();
    }

    public List<NotificationDTO> convertPageToListDTO(List<Notification> notificationList) {
        return notificationList.stream()
                .map(this::covertEntityToDTO)
                .collect(Collectors.toList());
    }
}
