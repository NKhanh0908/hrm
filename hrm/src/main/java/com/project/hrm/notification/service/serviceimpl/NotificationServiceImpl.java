package com.project.hrm.notification.service.serviceimpl;

import com.project.hrm.auth.service.AccountService;
import com.project.hrm.common.utils.IdGenerator;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.notification.dto.NotificationCreateDTO;
import com.project.hrm.notification.dto.NotificationDTO;
import com.project.hrm.notification.dto.NotificationFilterDTO;
import com.project.hrm.notification.entity.Notification;
import com.project.hrm.notification.mapper.NotificationMapper;
import com.project.hrm.notification.repository.NotificationRepository;
import com.project.hrm.notification.service.NotificationService;
import com.project.hrm.notification.specification.NotificationSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final AccountService accountService;
    private final SimpMessagingTemplate messagingTemplate;
    private final EmployeeService employeeService;


    @Transactional
    @Override
    public NotificationDTO create(NotificationCreateDTO notificationCreateDTO) {
        log.info("Creating notification: {}", notificationCreateDTO);

        Notification notification = notificationMapper.covertCreateDTOToEntity(notificationCreateDTO);
        notification.setId(IdGenerator.getGenerationId());

        if(notificationCreateDTO.getSender() != null) {
            Employees sender = employeeService.getEntityById(notificationCreateDTO.getSender());
            notification.setSender(sender);
        }

        Employees recipient = employeeService.getEntityById(notificationCreateDTO.getRecipient());
        notification.setRecipient(recipient);

        log.info("Notification save: {}", notification);

        Notification savedNotification = notificationRepository.save(notification);

        NotificationDTO notificationDTO = notificationMapper.covertEntityToDTO(savedNotification);

        pushNotificationToUser(recipient, notificationDTO);

        return notificationDTO;
    }

    @Transactional
    @Override
    public NotificationDTO getDTOById(Integer id) {
        return notificationMapper.covertEntityToDTO(getEntityById(id));
    }

    @Transactional
    @Override
    public Notification getEntityById(Integer id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    @Transactional
    @Override
    public NotificationDTO markAsRead(Integer id) {
        Notification notification = getEntityById(id);
        notification.setRead(true);
        notificationRepository.save(notification);
        return notificationMapper.covertEntityToDTO(notification);
    }

    /**
     *
     * Check if a notification exists by its reference ID.
     * @param module  the module name to which the notification belongs
     * @param id the reference ID of the notification
     * @return true if a notification exists with the given reference ID, false otherwise
     */
    @Override
    public boolean existsNotificationByReferenceId(String module, Integer id) {
        return notificationRepository.existsNotificationByReferenceId(module, id);
    }

    @Transactional
    @Override
    public List<NotificationDTO> markAllAsRead(Integer recipientId) {
        List<Notification> notifications = notificationRepository.findAllByRecipientIdAndIsReadFalse(recipientId);
        for (Notification notification : notifications) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);
        return notificationMapper.convertPageToListDTO(notifications);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        notificationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationDTO> filter(NotificationFilterDTO notificationFilterDTO, Integer page, Integer size) {
        Specification<Notification> specification = NotificationSpecification.filter(notificationFilterDTO);
        Pageable pageable = PageRequest.of(page, size);
        return notificationMapper.convertPageToListDTO(
                notificationRepository.findAll(specification, pageable).getContent());
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationDTO> getNotificationsForCurrentEmployee() {
        Employees currentEmployee = accountService.getPrincipal();
        List<Notification> notifications = notificationRepository.findByRecipientId(currentEmployee.getId());
        return notificationMapper.convertPageToListDTO(notifications);
    }

    private void pushNotificationToUser(Employees recipient, NotificationDTO notificationDTO) {
        String account = accountService.getUsernameByEmployeeId(recipient.getId());
        messagingTemplate.convertAndSendToUser(
                account,
                "/queue/notifications",
                notificationDTO
        );
    }

}
