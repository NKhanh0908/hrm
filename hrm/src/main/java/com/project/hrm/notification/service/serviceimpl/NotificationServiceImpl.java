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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Notification notification = notificationMapper.covertCreateDTOToEntity(notificationCreateDTO);
        notification.setId(IdGenerator.getGenerationId());

        Employees recipient = employeeService.getEntityById(notificationCreateDTO.getRecipient());
        notification.setRecipient(recipient);

        Notification savedNotification = notificationRepository.save(notification);

        // Gửi thông báo đến người dùng
        pushNotificationToUser(recipient, savedNotification);
        return notificationMapper.covertEntityToDTO(savedNotification);
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

    @Transactional
    @Override
    public List<NotificationDTO> filter(NotificationFilterDTO notificationFilterDTO, Integer page, Integer size) {
        Specification<Notification> specification = NotificationSpecification.filter(notificationFilterDTO);
        Pageable pageable = PageRequest.of(page, size);
        return notificationMapper.convertPageToListDTO(
                notificationRepository.findAll(specification, pageable).getContent());
    }

    @Transactional
    @Override
    public List<NotificationDTO> getNotificationsForCurrentEmployee() {
        Employees currentEmployee = accountService.getPrincipal();
        List<Notification> notifications = notificationRepository.findByRecipientId(currentEmployee.getId());
        return notificationMapper.convertPageToListDTO(notifications);
    }

    public void pushNotificationToUser(Employees recipient, Notification notification) {
        NotificationDTO notificationDTO = notificationMapper.covertEntityToDTO(notification);
        // Gửi đến user theo username (cần đảm bảo username là unique và client subscribe đúng topic)
        messagingTemplate.convertAndSendToUser(
                recipient.fullName(),  // user destination (user’s unique name)
                "/queue/notifications",   // topic dành cho user này
                notificationDTO
        );
    }

}
