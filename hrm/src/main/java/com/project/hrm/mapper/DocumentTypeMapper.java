package com.project.hrm.mapper;

import com.project.hrm.dto.documentTypeDTO.DocumentTypeCreateDTO;
import com.project.hrm.dto.documentTypeDTO.DocumentTypeDTO;
import com.project.hrm.entities.DocumentTypes;

import java.util.List;
import java.util.stream.Collectors;

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
