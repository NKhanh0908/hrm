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
     * @return Notification if it exists, otherwise null
     */
    @Override
    public Notification existsNotificationByReferenceId(String module, Integer id) {
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

    /**
     * Sends a training request notification to the employee and the target employee.
     *
     * @param trainingRequestDTO the training request data transfer object containing details of the request.
     */
    @Transactional
    @Override
    public void sendTrainingRequest(TrainingRequestDTO trainingRequestDTO) {
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

    private void pushNotificationToUser(Employees recipient, NotificationDTO notificationDTO) {
        String account = accountService.getUsernameByEmployeeId(recipient.getId());
        messagingTemplate.convertAndSendToUser(
                account,
                "/queue/notifications",
                notificationDTO
        );
    }

}
