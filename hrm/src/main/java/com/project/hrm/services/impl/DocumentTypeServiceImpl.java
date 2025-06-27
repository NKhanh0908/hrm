package com.project.hrm.services.impl;

import com.project.hrm.dto.documentTypeDTO.DocumentTypeCreateDTO;
import com.project.hrm.dto.documentTypeDTO.DocumentTypeDTO;
import com.project.hrm.dto.documentTypeDTO.DocumentTypeUpdateDTO;
import com.project.hrm.entities.DocumentTypes;
import com.project.hrm.mapper.DocumentTypeMapper;
import com.project.hrm.repositories.DocumentTypesRepository;
import com.project.hrm.services.DocumentTypeService;
import com.project.hrm.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.DocumentType;


@Service
@AllArgsConstructor
@Slf4j
public class DocumentTypeServiceImpl implements DocumentTypeService {
    private final DocumentTypesRepository documentTypesRepository;
    private final DocumentTypeMapper documentTypeMapper;

    @Override
    public DocumentTypeDTO create(DocumentTypeCreateDTO documentTypeCreateDTO) {
        log.info("Create DocumentTypeDTO with name: {}", documentTypeCreateDTO.getName());
        DocumentTypes documentType = documentTypeMapper.covertCreateDTOToEntity(documentTypeCreateDTO);

        documentType.setId(IdGenerator.getGenerationId());
        if (documentTypesRepository.existsByName(documentType.getName())) {
            // chua viet Exception nay
        }

        return documentTypeMapper.covertEntityToDTO(documentTypesRepository.save(documentType));
    }

    @Override
    public DocumentTypeDTO update(DocumentTypeUpdateDTO documentTypeUpdateDTO) {
        DocumentTypes documentType = getEntityById(documentTypeUpdateDTO.getId());

        if(documentTypeUpdateDTO.getName() != null) {
            documentType.setName(documentTypeUpdateDTO.getName());
        }

        if(documentTypeUpdateDTO.getDescription() != null) {
            documentType.setDescription(documentTypeUpdateDTO.getDescription());
        }

        log.info("Update DocumentTypeDTO with id: {}", documentType.getId());

        return documentTypeMapper.covertEntityToDTO(documentType);
    }

    @Override
    public DocumentTypeDTO getDTOById(Integer id) {
        return documentTypeMapper.covertEntityToDTO(getEntityById(id));
    }

    @Override
    public DocumentTypes getEntityById(Integer id) {
        return documentTypesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document type not found with id: " + id));
    }

}
