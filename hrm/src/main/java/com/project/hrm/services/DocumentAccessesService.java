package com.project.hrm.services;

import com.project.hrm.dto.documentAccessesDTO.DocumentAccessesCreateDTO;
import com.project.hrm.dto.documentAccessesDTO.DocumentAccessesDTO;
import com.project.hrm.dto.documentAccessesDTO.DocumentAccessesUpdateDTO;
import com.project.hrm.entities.DocumentAccesses;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocumentAccessesService {
    public DocumentAccessesDTO create(DocumentAccessesCreateDTO documentAccessesCreateDTO);
    public DocumentAccessesDTO getDTOById(Integer id);
    public DocumentAccessesDTO update(DocumentAccessesUpdateDTO documentAccessesUpdateDTO);
    public DocumentAccesses getEntityById(Integer id);
    public List<DocumentAccesses> filterByDocumentId(Integer documentId,String accessLevel, String employeeName, int page, int size);
}
