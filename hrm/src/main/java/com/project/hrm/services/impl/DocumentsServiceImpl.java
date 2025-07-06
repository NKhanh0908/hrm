package com.project.hrm.services.impl;

import com.project.hrm.dto.documentsDTO.DocumentsCreateDTO;
import com.project.hrm.dto.documentsDTO.DocumentsDTO;
import com.project.hrm.dto.documentsDTO.DocumentsUpdateDTO;
import com.project.hrm.entities.Documents;
import com.project.hrm.enums.DocumentsStatus;
import com.project.hrm.mapper.DocumentsMapper;
import com.project.hrm.repositories.DocumentRepository;
import com.project.hrm.services.AccountService;
import com.project.hrm.services.DocumentTypeService;
import com.project.hrm.services.DocumentsService;
import com.project.hrm.services.FileService;
import com.project.hrm.utils.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@AllArgsConstructor
public class DocumentsServiceImpl implements DocumentsService {
    private final DocumentRepository documentRepository;
    private final DocumentsMapper documentsMapper;
    private final FileService imageEmployeeService;
    private final AccountService accountService;
    private final DocumentTypeService documentTypeService;

    @Transactional
    @Override
    public DocumentsDTO create(DocumentsCreateDTO documentsCreateDTO) {
        Documents documents = documentsMapper.convertCreateDTOToEntity(documentsCreateDTO);
        documents.setId(IdGenerator.getGenerationId());


        if (documentsCreateDTO.getFile() != null) {
            Map<String, Object> fileDetails = imageEmployeeService.saveFile(
                    documentsCreateDTO.getFile()
            );
            documents.setFilePath((String) fileDetails.get("url"));
            documents.setFileType((String) fileDetails.get("originalFileName"));
            documents.setFileSize((Long) fileDetails.get("fileSize"));
        }

        documents.setUploadedBy(accountService.getPrincipal());
        documents.setDocumentTypes(documentTypeService.getEntityById(documentsCreateDTO.getDocumentTypeId()));

        return documentsMapper.convertEntityToDTO(documentRepository.save(documents));
    }

    @Transactional
    @Override
    public DocumentsDTO update(DocumentsUpdateDTO documentsUpdateDTO) {
        Documents existingDocument = getEntityById(documentsUpdateDTO.getId());

        if(documentsUpdateDTO.getTitle() != null) {
            existingDocument.setTitle(documentsUpdateDTO.getTitle());
        }
        if(documentsUpdateDTO.getDescription() != null) {
            existingDocument.setDescription(documentsUpdateDTO.getDescription());
        }
        if(documentsUpdateDTO.getDocumentStatus() != null) {
            existingDocument.setDocumentStatus(DocumentsStatus.valueOf(documentsUpdateDTO.getDocumentStatus()));
        }
        if(documentsUpdateDTO.getUploadedById() != null) {
            existingDocument.setUploadedBy(accountService.getPrincipal());
        }
        if(documentsUpdateDTO.getDocumentTypeId() != null) {
            existingDocument.setDocumentTypes(documentTypeService.getEntityById(documentsUpdateDTO.getDocumentTypeId()));
        }

        return documentsMapper.convertEntityToDTO(documentRepository.save(existingDocument));
    }

    @Transactional(readOnly = true)
    @Override
    public DocumentsDTO getDTOById(Integer id) {
        return documentsMapper.convertEntityToDTO(getEntityById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Documents getEntityById(Integer id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
    }
}
