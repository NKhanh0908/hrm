package com.project.hrm.document.service;

import com.project.hrm.document.dto.documentAccessesDTO.DocumentAccessesCreateDTO;
import com.project.hrm.document.dto.documentAccessesDTO.DocumentAccessesDTO;
import com.project.hrm.document.dto.documentAccessesDTO.DocumentAccessesUpdateDTO;
import com.project.hrm.document.entity.DocumentAccesses;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocumentAccessesService {
    public DocumentAccessesDTO create(DocumentAccessesCreateDTO documentAccessesCreateDTO);
    public DocumentAccessesDTO getDTOById(Integer id);
    public DocumentAccessesDTO update(DocumentAccessesUpdateDTO documentAccessesUpdateDTO);
    public DocumentAccesses getEntityById(Integer id);
    public List<DocumentAccessesDTO> filterByDocumentId(Integer documentId,String accessLevel, String employeeName, int page, int size);
}
