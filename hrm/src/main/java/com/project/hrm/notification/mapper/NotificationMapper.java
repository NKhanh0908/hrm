package com.project.hrm.notification.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.notification.dto.NotificationCreateDTO;
import com.project.hrm.notification.dto.NotificationDTO;
import com.project.hrm.notification.entity.Notification;
import com.project.hrm.notification.enums.NotificationType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class NotificationMapper {
    public Notification covertCreateDTOToEntity(NotificationCreateDTO notificationCreateDTO) {
        return Notification.builder()
                .title(notificationCreateDTO.getTitle())
                .message(notificationCreateDTO.getMessage())
                .notificationType(NotificationType.valueOf(notificationCreateDTO.getNotificationType()))
                .senderType(notificationCreateDTO.getSenderType())
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .module(notificationCreateDTO.getModule() != null ? notificationCreateDTO.getModule() : null)
                .metadata(notificationCreateDTO.getMetadata() != null ? notificationCreateDTO.getMetadata() : null)
                .referenceId(notificationCreateDTO.getReferenceId() != null ? notificationCreateDTO.getReferenceId() : null)
                .build();
    }

    public NotificationDTO covertEntityToDTO(Notification notification) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> metadataMap = null;
        try {
            if (notification.getMetadata() != null) {
                metadataMap = objectMapper.readValue(notification.getMetadata(), new TypeReference<Map<String, Object>>() {});
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return NotificationDTO.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .recipientId(notification.getRecipient().getId())
                .senderId(notification.getSender() != null ? notification.getSender().getId() : null)
                .senderName(notification.getSender() != null ? notification.getSender().fullName() : null)
                .notificationType(notification.getNotificationType())
                .senderType(notification.getSenderType())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .module(notification.getModule())
                .referenceId(notification.getReferenceId() != null ? notification.getReferenceId() : null)
                .metadata(metadataMap) // ← Dưới dạng object JSON
                .build();
    }


    public List<NotificationDTO> convertPageToListDTO(List<Notification> notificationList) {
        return notificationList.stream()
                .map(this::covertEntityToDTO)
                .collect(Collectors.toList());
    }
}
