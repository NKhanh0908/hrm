package com.project.hrm.document.service;

import com.project.hrm.document.dto.documentDTO.DocumentFilterDTO;
import com.project.hrm.document.dto.documentApprovalsDTO.DocumentApprovalsCreateDTO;
import com.project.hrm.document.dto.documentApprovalsDTO.DocumentApprovalsDTO;
import com.project.hrm.document.dto.documentApprovalsDTO.DocumentApprovalsUpdateDTO;
import com.project.hrm.document.entity.DocumentApprovals;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocumentApprovalsService {
    DocumentApprovalsDTO create(DocumentApprovalsCreateDTO documentApprovalsCreateDTO);

    DocumentApprovalsDTO update(DocumentApprovalsUpdateDTO documentApprovalsUpdateDTO);

    DocumentApprovalsDTO getDTOById(Integer id);

    DocumentApprovals getEntityById(Integer id);

    DocumentApprovalsDTO updateStatus(Integer id, String status);

    List<DocumentApprovalsDTO> filterByStatus(DocumentFilterDTO filterDTO, Integer page, Integer size);
}
