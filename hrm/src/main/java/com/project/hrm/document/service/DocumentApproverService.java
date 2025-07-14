package com.project.hrm.document.service;

import com.project.hrm.document.dto.documentApproverDTO.DocumentApproverCreateDTO;
import com.project.hrm.document.dto.documentApproverDTO.DocumentApproverDTO;
import com.project.hrm.document.entity.DocumentApprover;
import com.project.hrm.document.entity.Documents;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocumentApproverService {
    List<DocumentApproverDTO> createApproversForDocument(Documents document);
    DocumentApproverDTO create(DocumentApproverCreateDTO documentApproverCreateDTO);
    DocumentApproverDTO update(DocumentApproverDTO documentApproverDTO);
    DocumentApproverDTO getDTOById(Integer id);
    DocumentApprover getEntityById(Integer id);
    void deleteApproversByDocumentId(Integer documentId);
}
