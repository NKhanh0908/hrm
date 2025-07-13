package com.project.hrm.notification.service;

import com.project.hrm.notification.dto.NotificationCreateDTO;
import com.project.hrm.notification.dto.NotificationDTO;
import com.project.hrm.notification.dto.NotificationFilterDTO;
import com.project.hrm.notification.entity.Notification;

import java.util.List;

public interface NotificationService {
    NotificationDTO create(NotificationCreateDTO notificationCreateDTO);
    NotificationDTO getDTOById(Integer id);
    Notification getEntityById(Integer id);
    NotificationDTO markAsRead(Integer id);
    List<NotificationDTO> markAllAsRead(Integer recipientId);
    void delete(Integer id);
    List<NotificationDTO> filter(NotificationFilterDTO notificationFilterDTO, Integer page, Integer size);
    List<NotificationDTO> getNotificationsForCurrentEmployee();
}
