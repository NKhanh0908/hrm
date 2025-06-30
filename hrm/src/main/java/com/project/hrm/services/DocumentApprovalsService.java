package com.project.hrm.services;

import com.project.hrm.dto.DocumentFilter.DocumentFilterDTO;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsCreateDTO;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsDTO;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsUpdateDTO;
import com.project.hrm.entities.DocumentApprovals;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
