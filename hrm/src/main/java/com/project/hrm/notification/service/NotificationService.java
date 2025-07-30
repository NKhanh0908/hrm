package com.project.hrm.notification.service;

import com.project.hrm.notification.dto.NotificationCreateDTO;
import com.project.hrm.notification.dto.NotificationDTO;
import com.project.hrm.notification.dto.NotificationFilterDTO;
import com.project.hrm.notification.entity.Notification;
import com.project.hrm.recruitment.dto.applyDTO.ApplyDTO;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementsDTO;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestDTO;

import java.util.List;

public interface NotificationService {
    NotificationDTO create(NotificationCreateDTO notificationCreateDTO);

    NotificationDTO getDTOById(Integer id);

    Notification getEntityById(Integer id);

    NotificationDTO markAsRead(Integer id);

    Notification existsNotificationByReferenceId(String module, Integer id);

    List<NotificationDTO> markAllAsRead(Integer recipientId);

    void delete(Integer id);

    List<NotificationDTO> filter(NotificationFilterDTO notificationFilterDTO, Integer page, Integer size);

    List<NotificationDTO> getNotificationsForCurrentEmployee();

    void sendTrainingRequest(TrainingRequestDTO trainingRequestDTO);

    void sendRecruitmentRequest(RecruitmentRequirementsDTO recruitmentRequirementsDTO);

    void sendApply(ApplyDTO applyDTO);

}
