package com.project.hrm.services;

import com.project.hrm.dto.documentTypeDTO.DocumentTypeCreateDTO;
import com.project.hrm.dto.documentTypeDTO.DocumentTypeDTO;
import com.project.hrm.dto.documentTypeDTO.DocumentTypeUpdateDTO;
import com.project.hrm.entities.DocumentTypes;
import org.springframework.stereotype.Service;

@Service
public interface DocumentTypeService {
    DocumentTypeDTO create(DocumentTypeCreateDTO documentTypeCreateDTO);

    DocumentTypeDTO update(DocumentTypeUpdateDTO documentTypeUpdateDTO);

    DocumentTypeDTO getDTOById(Integer id);

    DocumentTypes getEntityById(Integer id);

}
