package com.project.hrm.mapper;

import com.project.hrm.dto.documentsDTO.DocumentsCreateDTO;
import com.project.hrm.dto.documentsDTO.DocumentsDTO;
import com.project.hrm.entities.Documents;
import com.project.hrm.enums.DocumentsStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentsMapper {
    public Documents convertCreateDTOToEntity(DocumentsCreateDTO documentsCreateDTO) {
        return Documents.builder()
                .title(documentsCreateDTO.getTitle())
                .description(documentsCreateDTO.getDescription())
                .uploadDate(LocalDateTime.now())
                .documentStatus(DocumentsStatus.PENDING)
                .build();
    }

    public  DocumentsDTO convertEntityToDTO(Documents documents) {
        return DocumentsDTO.builder()
                .id(documents.getId())
                .title(documents.getTitle())
                .description(documents.getDescription())
                .filePath(documents.getFilePath())
                .fileType(documents.getFileType())
                .fileSize(documents.getFileSize())
                .uploadDate(documents.getUploadDate())
                .documentStatus(documents.getDocumentStatus().name())
                .documentTypeId(documents.getDocumentTypes().getId())
                .uploadedById(documents.getUploadedBy().getId())
                .build();
    }
    public List<DocumentsDTO> convertPageToListDTO(List<Documents> documentsList) {
        return documentsList.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }
}
