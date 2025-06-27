package com.project.hrm.services.impl;

import com.project.hrm.dto.DocumentFilter.DocumentFilterDTO;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsCreateDTO;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsDTO;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsUpdateDTO;
import com.project.hrm.entities.DocumentApprovals;
import com.project.hrm.entities.Documents;
import com.project.hrm.entities.Employees;
import com.project.hrm.enums.DocumentApprovalsStatus;
import com.project.hrm.enums.EnrollmentStatus;
import com.project.hrm.mapper.DocumentApprovalsMapper;
import com.project.hrm.repositories.DocumentApprovalsRepository;
import com.project.hrm.services.AccountService;
import com.project.hrm.services.DocumentApprovalsService;
import com.project.hrm.services.DocumentsService;
import com.project.hrm.specifications.DocumentApprovalsSpecification;
import com.project.hrm.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DocumentApprovalsServiceImpl implements DocumentApprovalsService {
    private final DocumentApprovalsMapper documentApprovalsMapper;
    private final DocumentApprovalsRepository documentApprovalsRepository;
    private final DocumentsService documentsService;
    private final AccountService accountService;



    @Override
    public DocumentApprovalsDTO create(DocumentApprovalsCreateDTO documentApprovalsCreateDTO) {
        DocumentApprovals documentApprovals = documentApprovalsMapper.covertCreateDTOToEntity(documentApprovalsCreateDTO);

        documentApprovals.setId(IdGenerator.getGenerationId());
        if (documentApprovalsRepository.existsByDocumentsIdAndRequestedById(
                documentApprovals.getDocuments().getId(), documentApprovals.getRequestedBy().getId())) {
            // Chua viet Exception nay
        }
        Documents document = documentsService.getEntityById(documentApprovalsCreateDTO.getDocumentId());
        documentApprovals.setDocuments(document);
        documentApprovals.setRequestedBy(accountService.getPrincipal());

        return documentApprovalsMapper.covertEntityToDTO(documentApprovals);
    }

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

    @Override
    public DocumentApprovalsDTO getDTOById(Integer id) {
        return documentApprovalsMapper.covertEntityToDTO(getEntityById(id));
    }

    @Override
    public DocumentApprovals getEntityById(Integer id) {
        return documentApprovalsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DocumentApproval not found with id: " + id));
    }

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
        return documentApprovalsMapper.covertEntityToDTO(documentApprovalsRepository.save(documentApprovals));
    }

    @Override
    public List<DocumentApprovalsDTO> filterByStatus(DocumentFilterDTO filterDTO, Integer page, Integer size) {
        log.info("Filter DocumentApprovals by status: {}", filterDTO.getStatus());
        Specification<DocumentApprovals> spec = DocumentApprovalsSpecification.filterDocumentApprovals(filterDTO);
        Pageable pageable = PageRequest.of(page, size);
        return documentApprovalsMapper.convertPageToListDTO(
                documentApprovalsRepository.findAll(spec, pageable).getContent());
    }
}
