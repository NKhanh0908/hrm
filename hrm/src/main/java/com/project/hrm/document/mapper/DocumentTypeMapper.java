package com.project.hrm.document.mapper;

import com.project.hrm.document.dto.documentTypeDTO.DocumentTypeCreateDTO;
import com.project.hrm.document.dto.documentTypeDTO.DocumentTypeDTO;
import com.project.hrm.document.entity.DocumentTypes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentTypeMapper {
    public DocumentTypes covertCreateDTOToEntity(DocumentTypeCreateDTO documentTypeCreateDTO) {
        return DocumentTypes.builder()
                .name(documentTypeCreateDTO.getName())
                .description(documentTypeCreateDTO.getDescription())
                .build();
    }

    public DocumentTypeDTO covertEntityToDTO(DocumentTypes documentTypes) {
        return DocumentTypeDTO.builder()
                .id(documentTypes.getId())
                .name(documentTypes.getName())
                .description(documentTypes.getDescription())
                .build();
    }


    public List<DocumentTypeDTO> convertPageToListDTO(List<DocumentTypes> documentTypesList) {
        return documentTypesList.stream()
                .map(this::covertEntityToDTO)
                .collect(Collectors.toList());
    }
}
