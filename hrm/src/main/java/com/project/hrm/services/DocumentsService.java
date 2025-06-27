package com.project.hrm.services;

import com.project.hrm.dto.documentsDTO.DocumentsCreateDTO;
import com.project.hrm.dto.documentsDTO.DocumentsDTO;
import com.project.hrm.dto.documentsDTO.DocumentsUpdateDTO;
import com.project.hrm.entities.Documents;
import org.springframework.stereotype.Service;

@Service
public interface DocumentsService {
    DocumentsDTO create(DocumentsCreateDTO documentsCreateDTO);
    DocumentsDTO update(DocumentsUpdateDTO documentsUpdateDTO);
    DocumentsDTO getDTOById(Integer id);
    Documents getEntityById(Integer id);
}
