package com.project.hrm.document.mapper;

import com.project.hrm.document.dto.documentDTO.DocumentsCreateDTO;
import com.project.hrm.document.dto.documentDTO.DocumentsDTO;
import com.project.hrm.document.entity.Documents;
import com.project.hrm.document.enums.DocumentsStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
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
