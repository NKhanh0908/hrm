package com.project.hrm.document.service;

import com.project.hrm.document.dto.documentTypeDTO.DocumentTypeCreateDTO;
import com.project.hrm.document.dto.documentTypeDTO.DocumentTypeDTO;
import com.project.hrm.document.dto.documentTypeDTO.DocumentTypeUpdateDTO;
import com.project.hrm.document.entity.DocumentTypes;
import org.springframework.stereotype.Service;

@Service
public interface DocumentTypeService {
    DocumentTypeDTO create(DocumentTypeCreateDTO documentTypeCreateDTO);

    DocumentTypeDTO update(DocumentTypeUpdateDTO documentTypeUpdateDTO);

    DocumentTypeDTO getDTOById(Integer id);

    DocumentTypes getEntityById(Integer id);

}
