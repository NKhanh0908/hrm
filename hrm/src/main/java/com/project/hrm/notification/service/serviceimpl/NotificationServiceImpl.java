package com.project.hrm.notification.service.serviceimpl;

import com.project.hrm.common.utils.IdGenerator;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Transactional
    @Override
    public NotificationDTO create(NotificationCreateDTO notificationCreateDTO) {
        Notification notification = notificationMapper.covertCreateDTOToEntity(notificationCreateDTO);
        notification.setId(IdGenerator.getGenerationId());
        return notificationMapper.covertEntityToDTO(notificationRepository.save(notification));
    }

    @Override
    public NotificationDTO getDTOById(Integer id) {
        return notificationMapper.covertEntityToDTO(getEntityById(id));
    }

    @Override
    public Notification getEntityById(Integer id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    @Override
    public List<NotificationDTO> markAsRead(Integer id) {
        Notification notification = getEntityById(id);
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDTO> markAllAsRead(Integer recipientId) {
        List<Notification> notifications = notificationRepository.findAllByRecipientIdAndReadFalse(recipientId);
        for (Notification notification : notifications) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void delete(Integer id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<NotificationDTO> filter(NotificationFilterDTO notificationFilterDTO, Integer page, Integer size) {
        Specification<Notification> specification = NotificationSpecification.filter(notificationFilterDTO);
        Pageable pageable = PageRequest.of(page, size);
        return notificationMapper.convertPageToListDTO(
                notificationRepository.findAll(specification, pageable).getContent());
    }
}
