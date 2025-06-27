package com.project.hrm.mapper;

import com.project.hrm.dto.documentAccessesDTO.DocumentAccessesCreateDTO;
import com.project.hrm.dto.documentAccessesDTO.DocumentAccessesDTO;
import com.project.hrm.dto.documentsDTO.DocumentsCreateDTO;
import com.project.hrm.entities.DocumentAccesses;
import com.project.hrm.enums.DocumentAccess;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentAccessesMapper {
    public DocumentAccesses covertCreateDTOToEntity(DocumentAccessesCreateDTO documentAccessesCreateDTO) {
        return DocumentAccesses.builder()
                .accessedAt(LocalDateTime.now())
                .accessLevel(DocumentAccess.valueOf(documentAccessesCreateDTO.getAccessLevel()))
               .build();
    }

    public DocumentAccessesDTO covertEntityToDTO(DocumentAccesses documentAccesses) {
        return DocumentAccessesDTO.builder()
                .id(documentAccesses.getId())
                .documentName(documentAccesses.getDocuments().getTitle())
                .documentType(documentAccesses.getDocuments().getDocumentTypes().getName())
                .accessLevel(documentAccesses.getAccessLevel().name())
                .employeeName(documentAccesses.getEmployees().fullName())
                .employeeEmail(documentAccesses.getEmployees().getEmail())
                .documentId(documentAccesses.getDocuments().getId())
                .employeeId(documentAccesses.getEmployees().getId())
                .build();
    }

    public List<DocumentAccessesDTO> convertPageToListDTO(List<DocumentAccesses> documentAccessesList) {
        return documentAccessesList.stream()
                .map(this::covertEntityToDTO)
                .collect(Collectors.toList());
    }
}
