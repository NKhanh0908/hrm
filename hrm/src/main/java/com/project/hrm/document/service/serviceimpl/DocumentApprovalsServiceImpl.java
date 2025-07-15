package com.project.hrm.document.service.serviceimpl;

import com.project.hrm.document.dto.documentDTO.DocumentFilterDTO;
import com.project.hrm.document.dto.documentApprovalsDTO.DocumentApprovalsCreateDTO;
import com.project.hrm.document.dto.documentApprovalsDTO.DocumentApprovalsDTO;
import com.project.hrm.document.dto.documentApprovalsDTO.DocumentApprovalsUpdateDTO;
import com.project.hrm.document.entity.DocumentApprovals;
import com.project.hrm.document.entity.DocumentApprover;
import com.project.hrm.document.entity.Documents;
import com.project.hrm.document.enums.DocumentApprovalsStatus;
import com.project.hrm.document.mapper.DocumentApprovalsMapper;
import com.project.hrm.document.repository.DocumentApprovalsRepository;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.document.repository.DocumentApproverRepository;
import com.project.hrm.document.service.DocumentApprovalsService;
import com.project.hrm.document.service.DocumentsService;
import com.project.hrm.document.specification.DocumentApprovalsSpecification;
import com.project.hrm.common.utils.IdGenerator;
import com.project.hrm.notification.dto.NotificationCreateDTO;
import com.project.hrm.notification.enums.NotificationType;
import com.project.hrm.notification.enums.SenderType;
import com.project.hrm.notification.service.NotificationService;
import com.project.hrm.notification.utils.NotificationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DocumentApprovalsServiceImpl implements DocumentApprovalsService {
    private final DocumentApprovalsMapper documentApprovalsMapper;
    private final DocumentApprovalsRepository documentApprovalsRepository;
    private final DocumentsService documentsService;
    private final AccountService accountService;
    private final NotificationService notificationService;
    private final DocumentApproverRepository documentApproverRepository;

    @Transactional
    @Override
    public DocumentApprovalsDTO create(DocumentApprovalsCreateDTO documentApprovalsCreateDTO) {
        DocumentApprovals documentApprovals = documentApprovalsMapper.covertCreateDTOToEntity(documentApprovalsCreateDTO);

        documentApprovals.setId(IdGenerator.getGenerationId());
        //if (documentApprovalsRepository.existsByDocumentsIdAndRequestedById(
               // documentApprovals.getDocuments().getId(), documentApprovals.getRequestedBy().getId())) {
            // Chua viet Exception nay
        //}
        Documents document = documentsService.getEntityById(documentApprovalsCreateDTO.getDocumentId());
        documentApprovals.setDocuments(document);
        documentApprovals.setRequestedBy(accountService.getPrincipal());

        log.info(documentApprovals.toString());
        notifyApproversForDocument(document);


        return documentApprovalsMapper.covertEntityToDTO(documentApprovalsRepository.save(documentApprovals));
    }

    @Transactional
    @Override
    public DocumentApprovalsDTO update(DocumentApprovalsUpdateDTO documentApprovalsUpdateDTO) {
        DocumentApprovals documentApprovals = getEntityById(documentApprovalsUpdateDTO.getId());
        if (documentApprovalsUpdateDTO.getStatus() != null) {
            documentApprovals.setDocumentApprovalsStatus(DocumentApprovalsStatus.valueOf(documentApprovalsUpdateDTO.getStatus()));
        }

        if (documentApprovalsUpdateDTO.getReason() != null) {
            documentApprovals.setReason(documentApprovalsUpdateDTO.getReason());
        }

        if(documentApprovalsUpdateDTO.getDocumentId() != null) {
            if (documentApprovalsRepository.existsByDocumentsIdAndRequestedById(
                    documentApprovalsUpdateDTO.getDocumentId(), documentApprovals.getRequestedBy().getId())) {
                // Chua viet Exception nay
            }
            Documents document = documentsService.getEntityById(documentApprovalsUpdateDTO.getDocumentId());
            documentApprovals.setDocuments(document);
        }

        return documentApprovalsMapper.covertEntityToDTO(documentApprovalsRepository.save(documentApprovals));
    }

    @Transactional(readOnly = true)
    @Override
    public DocumentApprovalsDTO getDTOById(Integer id) {
        return documentApprovalsMapper.covertEntityToDTO(getEntityById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public DocumentApprovals getEntityById(Integer id) {
        return documentApprovalsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DocumentApproval not found with id: " + id));
    }

    @Transactional
    @Override
    public DocumentApprovalsDTO updateStatus(Integer id, String status) {
        DocumentApprovals documentApprovals = getEntityById(id);
        try {
            documentApprovals.setDocumentApprovalsStatus(DocumentApprovalsStatus.valueOf(status.toUpperCase()));
            documentApprovals.setApprovedBy(accountService.getPrincipal());
        } catch (IllegalArgumentException ex) {
            log.error("Invalid status '{}' supplied for TrainingEnrollment ID {}", status, id);
            throw ex;
        }
        DocumentApprovals saved = documentApprovalsRepository.save(documentApprovals);

        Integer requesterId = saved.getRequestedBy().getId();
        Integer approverId = saved.getApprovedBy().getId();
        String documentTitle = saved.getDocuments().getTitle();
        Integer documentId = saved.getDocuments().getId();

        switch (saved.getDocumentApprovalsStatus()) {
            case APPROVED -> {
                notificationService.create(NotificationCreateDTO.builder()
                        .title("Tài liệu đã được phê duyệt")
                        .message("Tài liệu '" + documentTitle + "' đã được duyệt bởi "
                                + saved.getApprovedBy().fullName())
                        .recipient(requesterId)
                        .sender(approverId)
                        .senderType(SenderType.EMPLOYEE)
                        .notificationType(NotificationType.DOCUMENT_APPROVED.name())
                        .module("document")
                        .referenceId(documentId)
                        .build());
            }
            case REJECTED -> {
                notificationService.create(NotificationCreateDTO.builder()
                        .title("Tài liệu bị từ chối")
                        .message("Tài liệu '" + documentTitle + "' đã bị từ chối bởi "
                                + saved.getApprovedBy().fullName())
                        .recipient(requesterId)
                        .sender(approverId)
                        .senderType(SenderType.EMPLOYEE)
                        .notificationType(NotificationType.DOCUMENT_REJECTED.name())
                        .module("document")
                        .referenceId(documentId)
                        .build());
            }
            case CANCELLED -> {
                notificationService.create(NotificationCreateDTO.builder()
                        .title("Yêu cầu phê duyệt bị hủy")
                        .message("Yêu cầu phê duyệt tài liệu '" + documentTitle + "' đã bị hủy.")
                        .recipient(requesterId)
                        .sender(approverId)
                        .senderType(SenderType.EMPLOYEE)
                        .notificationType(NotificationType.DOCUMENT_CANCELLED.name())
                        .module("document")
                        .referenceId(documentId)
                        .build());
            }
            default -> {
                // PENDING hoặc trạng thái khác không cần thông báo
            }
        }

        return documentApprovalsMapper.covertEntityToDTO(documentApprovalsRepository.save(documentApprovals));
    }

    @Transactional(readOnly = true)
    @Override
    public List<DocumentApprovalsDTO> filterByStatus(DocumentFilterDTO filterDTO, Integer page, Integer size) {
        log.info("Filter DocumentApprovals by status: {}", filterDTO.getStatus());
        Specification<DocumentApprovals> spec = DocumentApprovalsSpecification.filterDocumentApprovals(filterDTO);
        Pageable pageable = PageRequest.of(page, size);
        return documentApprovalsMapper.convertPageToListDTO(
                documentApprovalsRepository.findAll(spec, pageable).getContent());
    }

    public void notifyApproversForDocument(Documents document) {
        List<DocumentApprover> approvers = documentApproverRepository.findByDocumentId(document.getId());
        for (DocumentApprover da : approvers) {
            notificationService.create(NotificationCreateDTO.builder()
                    .title("Tài liệu cần phê duyệt")
                    .message("Bạn có một tài liệu mới cần duyệt: " + document.getTitle())
                    .recipient(da.getApprover().getId())
                    .sender(document.getUploadedBy().getId())
                    .senderType(SenderType.EMPLOYEE)
                    .notificationType(NotificationType.DOCUMENT_APPROVAL.name())
                    .module("document")
                    .referenceId(document.getId())
                    .build());
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void expirePendingApprovals() {
        List<DocumentApprovals> pendingApprovals = documentApprovalsRepository.findByDocumentApprovalsStatus(DocumentApprovalsStatus.PENDING);

        for (DocumentApprovals approval : pendingApprovals) {
            // Thời gian giới hạn: 3 ngày kể từ createdAt
            LocalDateTime expiredThreshold = approval.getRequestedAt().plusDays(3);
            if (expiredThreshold.isBefore(LocalDateTime.now())) {
                approval.setDocumentApprovalsStatus(DocumentApprovalsStatus.EXPIRED);
                documentApprovalsRepository.save(approval);

                // Gửi thông báo
                notificationService.create(NotificationCreateDTO.builder()
                        .title("Yêu cầu phê duyệt đã hết hạn")
                        .message("Tài liệu '" + approval.getDocuments().getTitle() + "' đã hết hạn phê duyệt.")
                        .recipient(approval.getRequestedBy().getId())
                        .senderType(SenderType.SYSTEM)
                        .notificationType(NotificationType.DOCUMENT_EXPIRED.name())
                        .module("document")
                        .referenceId(approval.getDocuments().getId())
                        .build());
            }
        }
    }
}
