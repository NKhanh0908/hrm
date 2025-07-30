package com.project.hrm.notification.service.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.common.utils.IdGenerator;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.notification.dto.NotificationCreateDTO;
import com.project.hrm.notification.dto.NotificationDTO;
import com.project.hrm.notification.dto.NotificationFilterDTO;
import com.project.hrm.notification.entity.Notification;
import com.project.hrm.notification.enums.NotificationType;
import com.project.hrm.notification.enums.SenderType;
import com.project.hrm.notification.mapper.NotificationMapper;
import com.project.hrm.notification.repository.NotificationRepository;
import com.project.hrm.notification.service.NotificationService;
import com.project.hrm.notification.specification.NotificationSpecification;
import com.project.hrm.recruitment.dto.applyDTO.ApplyDTO;
import com.project.hrm.recruitment.dto.recruitmentRequirementDTO.RecruitmentRequirementsDTO;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestDTO;
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

    /**
     * Creates a new notification based on the provided NotificationCreateDTO.
     *
     * @param notificationCreateDTO the data transfer object containing the details of the notification to be created.
     * @return NotificationDTO containing the details of the created notification.
     */
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

    /**
     * Creates a global notification that is sent to all users.
     *
     * @param notificationCreateDTO the data transfer object containing the details of the notification to be created.
     * @return NotificationDTO containing the details of the created global notification.
     */
    @Transactional
    @Override
    public NotificationDTO createGlobalNotification(NotificationCreateDTO notificationCreateDTO) {
        Notification notification = notificationMapper.covertCreateDTOToEntity(notificationCreateDTO);

        String sender = accountService.getAccountAuth().getRole().name();

        notification.setId(IdGenerator.getGenerationId());
        notification.setNotificationType(NotificationType.GLOBAL_ANNOUNCEMENT);
        notification.setSenderType(SenderType.valueOf(sender));

        NotificationDTO notificationDTO = notificationMapper.covertEntityToDTO(notificationRepository.save(notification));

        pushGlobalNotification(notificationDTO);

        return notificationDTO;
    }

    /**
     * Retrieves a notification by its ID and converts it to a NotificationDTO.
     *
     * @param id the ID of the notification to retrieve.
     * @return NotificationDTO containing the details of the notification.
     */
    @Transactional
    @Override
    public NotificationDTO getDTOById(Integer id) {
        return notificationMapper.covertEntityToDTO(getEntityById(id));
    }

    /**
     * Retrieves a notification entity by its ID.
     *
     * @param id the ID of the notification to retrieve.
     * @return Notification entity if found, otherwise throws an exception.
     */
    @Transactional
    @Override
    public Notification getEntityById(Integer id) {
        log.info("Fetching notification with ID: {}", id);

        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    /**
     * Marks a notification as read by its ID and returns the updated NotificationDTO.
     *
     * @param id the ID of the notification to mark as read.
     * @return NotificationDTO containing the updated details of the notification.
     */
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
     * @return Notification if it exists, otherwise null
     */
    @Override
    public Notification existsNotificationByReferenceId(String module, Integer id) {
        log.info("Checking if notification exists for module: {}, id: {}", module, id);

        return notificationRepository.existsNotificationByReferenceId(module, id);
    }

    /**
     * Marks all notifications as read for a specific recipient.
     *
     * @param recipientId the ID of the recipient whose notifications are to be marked as read.
     */
    @Transactional
    @Override
    public void markAllAsRead(Integer recipientId) {
        log.info("Marking all notifications as read for recipient ID: {}", recipientId);

        notificationRepository.markAllAsRead(recipientId);
    }

    /**
     * Deletes a notification by its ID.
     *
     * @param id the ID of the notification to delete.
     */
    @Transactional
    @Override
    public void delete(Integer id) {
        notificationRepository.deleteById(id);
    }

    /**
     * Filters notifications based on the provided filter criteria and pagination parameters.
     *
     * @param notificationFilterDTO the filter criteria for notifications.
     * @param page the page number for pagination.
     * @param size the size of each page.
     * @return List of NotificationDTOs that match the filter criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public List<NotificationDTO> filter(NotificationFilterDTO notificationFilterDTO, Integer page, Integer size) {
        log.info("Filtering notifications with criteria: {}, page: {}, size: {}", notificationFilterDTO, page, size);
        Specification<Notification> specification = NotificationSpecification.filter(notificationFilterDTO);
        Pageable pageable = PageRequest.of(page, size);
        return notificationMapper.convertPageToListDTO(
                notificationRepository.findAll(specification, pageable).getContent());
    }

    /**
     * Retrieves notifications for the current employee.
     *
     * @return List of NotificationDTOs for the current employee.
     */
    @Transactional(readOnly = true)
    @Override
    public List<NotificationDTO> getNotificationsForCurrentEmployee() {
        log.info("Fetching notifications for the current employee");
        Employees currentEmployee = accountService.getPrincipal();
        List<Notification> notifications = notificationRepository.findByRecipientId(currentEmployee.getId());
        return notificationMapper.convertPageToListDTO(notifications);
    }

    /**
     * Sends a training request notification to the employee and the target employee.
     *
     * @param trainingRequestDTO the training request data transfer object containing details of the request.
     */
    @Transactional
    @Override
    public void sendTrainingRequest(TrainingRequestDTO trainingRequestDTO) {
        log.info("Sending training request notification for request ID: {}", trainingRequestDTO.getId());
        String metadataJson = mapperMetadataToJson(trainingRequestDTO);

        NotificationCreateDTO createDTO = new NotificationCreateDTO();
        createDTO.setTitle("Training Request Status Update");
        createDTO.setMessage("Your training request has been updated to " + trainingRequestDTO.getStatus().name() + ".");
        createDTO.setSender(accountService.getPrincipal().getId());
        createDTO.setSenderType(SenderType.EMPLOYEE);
        createDTO.setRecipient(trainingRequestDTO.getEmployeeRequestId());
        createDTO.setNotificationType("TRAINING_REQUEST_UPDATE");
        createDTO.setModule("TRAINING");
        createDTO.setReferenceId(trainingRequestDTO.getId());
        createDTO.setMetadata(metadataJson);

        create(createDTO);

        createDTO.setRecipient(trainingRequestDTO.getTargetEmployeeId());
        create(createDTO);
    }

    /**
     * Sends a recruitment request notification to the employee.
     *
     * @param recruitmentRequirementsDTO the recruitment requirements data transfer object containing details of the recruitment.
     */
    @Transactional
    @Override
    public void sendRecruitmentRequest(RecruitmentRequirementsDTO recruitmentRequirementsDTO) {
        log.info("Sending recruitment requirement notification for requirement ID: {}", recruitmentRequirementsDTO.getId());
        String metadataJson = mapperMetadataToJson(recruitmentRequirementsDTO);

        NotificationCreateDTO createDTO = new NotificationCreateDTO();
        createDTO.setTitle("Recruitment Requirement Update");
        createDTO.setMessage("Recruitment requirement with ID " + recruitmentRequirementsDTO.getId() + " has been updated. " +
                "Please check the details for more information.");
        createDTO.setSender(accountService.getPrincipal().getId());
        createDTO.setSenderType(SenderType.EMPLOYEE);
        createDTO.setRecipient(recruitmentRequirementsDTO.getEmployeeId());
        createDTO.setNotificationType("RECRUITMENT_RESULT");
        createDTO.setModule("RECRUITMENT");
        createDTO.setReferenceId(recruitmentRequirementsDTO.getId());
        createDTO.setMetadata(metadataJson);

        create(createDTO);
    }

    /**
     * Sends a notification when an application is updated.
     *
     * @param applyDTO the application data transfer object containing details of the application.
     */
    @Transactional
    @Override
    public void sendApply(ApplyDTO applyDTO) {
        log.info("Sending application update notification for application ID: {}", applyDTO.getId());
        Notification notification = existsNotificationByReferenceId("APPLY", applyDTO.getId());
        if(notification != null) {
            notification.setRead(false);
            notificationRepository.save(notification);
            return;
        }

        String metadataJson = mapperMetadataToJson(applyDTO);

        NotificationCreateDTO createDTO = new NotificationCreateDTO();
        createDTO.setTitle("Recruitment Application Update");
        createDTO.setMessage("Your application for the position has been updated. Please check your profile for details.");
        createDTO.setSenderType(SenderType.EMPLOYEE);
        createDTO.setRecipient(applyDTO.getRecruitmentDTO().getRecruitmentRequirementsDTO().getEmployeeId());
        createDTO.setNotificationType("APPLY");
        createDTO.setModule("APPLY");
        createDTO.setReferenceId(applyDTO.getId());
        createDTO.setMetadata(metadataJson);

        create(createDTO);
    }

    private String mapperMetadataToJson(Object metadata) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(metadata);
        } catch (JsonProcessingException e) {
            log.error("Error converting metadata to JSON", e);
            return null;
        }
    }

    private void pushGlobalNotification(NotificationDTO notificationDTO) {
        log.info("Pushing global notification: {}", notificationDTO.getId());
        messagingTemplate.convertAndSend("/topic/notifications", notificationDTO);
    }

    private void pushNotificationToUser(Employees recipient, NotificationDTO notificationDTO) {
        log.info("Pushing notification to user: {}, notification ID: {}", recipient.getId(), notificationDTO.getId());
        String account = accountService.getUsernameByEmployeeId(recipient.getId());
        messagingTemplate.convertAndSendToUser(
                account,
                "/queue/notifications",
                notificationDTO
        );
    }

}
