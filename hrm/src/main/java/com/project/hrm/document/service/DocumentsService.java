package com.project.hrm.document.service;

import com.project.hrm.document.dto.documentDTO.DocumentsCreateDTO;
import com.project.hrm.document.dto.documentDTO.DocumentsDTO;
import com.project.hrm.document.dto.documentDTO.DocumentsUpdateDTO;
import com.project.hrm.document.entity.Documents;
import org.springframework.stereotype.Service;

@Service
public interface DocumentsService {
    DocumentsDTO create(DocumentsCreateDTO documentsCreateDTO);
    DocumentsDTO update(DocumentsUpdateDTO documentsUpdateDTO);
    DocumentsDTO getDTOById(Integer id);
    Documents getEntityById(Integer id);
}
