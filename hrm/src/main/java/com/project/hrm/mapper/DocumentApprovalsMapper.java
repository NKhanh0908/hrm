package com.project.hrm.mapper;

import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsCreateDTO;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsDTO;
import com.project.hrm.entities.DocumentApprovals;
import com.project.hrm.enums.DocumentApprovalsStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class DocumentApprovalsMapper {
    public DocumentApprovals covertCreateDTOToEntity(DocumentApprovalsCreateDTO documentApprovalsCreateDTO) {
        return DocumentApprovals.builder()
                .documentApprovalsStatus(DocumentApprovalsStatus.PENDING)
                .reason(documentApprovalsCreateDTO.getReason())
                .requestedAt(LocalDateTime.now())
                .build();
    }

    public DocumentApprovalsDTO covertEntityToDTO(DocumentApprovals documentApprovals) {
        return DocumentApprovalsDTO.builder()
                .id(documentApprovals.getId())
                .approvalDate(documentApprovals.getApprovalDate() != null ? documentApprovals.getApprovalDate() : null)
                .status(documentApprovals.getDocumentApprovalsStatus().name())
                .reason(documentApprovals.getReason())
                .documentId(documentApprovals.getDocuments().getId())
                .requestedById(documentApprovals.getRequestedBy().getId())
                .requestedByName(documentApprovals.getRequestedBy().fullName())
                .approvedById(documentApprovals.getApprovedBy() != null ? documentApprovals.getApprovedBy().getId() : null)
                .approverByName(documentApprovals.getApprovedBy() != null ? documentApprovals.getApprovedBy().fullName() : null)
                .documentName(documentApprovals.getDocuments().getTitle())
                .requestedAt(documentApprovals.getRequestedAt())
                .build();
    }

    public List<DocumentApprovalsDTO> convertPageToListDTO(List<DocumentApprovals> documentApprovalsList) {
        return documentApprovalsList.stream()
                .map(this::covertEntityToDTO)
                .collect(Collectors.toList());
    }
}
