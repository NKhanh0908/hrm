package com.project.hrm.document.service.serviceimpl;

import com.project.hrm.department.service.DepartmentService;
import com.project.hrm.document.dto.documentDTO.DocumentsCreateDTO;
import com.project.hrm.document.dto.documentDTO.DocumentsDTO;
import com.project.hrm.document.dto.documentDTO.DocumentsUpdateDTO;
import com.project.hrm.document.entity.Documents;
import com.project.hrm.document.enums.DocumentsStatus;
import com.project.hrm.document.mapper.DocumentsMapper;
import com.project.hrm.document.repository.DocumentRepository;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.document.service.DocumentApproverService;
import com.project.hrm.document.service.DocumentTypeService;
import com.project.hrm.document.service.DocumentsService;
import com.project.hrm.common.service.FileService;
import com.project.hrm.common.utils.IdGenerator;
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
    private final DepartmentService departmentService;
    private final DocumentApproverService documentApproverService;

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

        documents.setDepartment(departmentService.getEntityById(documentsCreateDTO.getDepartmentId()));
        documents.setUploadedBy(accountService.getPrincipal());

        documents.setDocumentTypes(documentTypeService.getEntityById(documentsCreateDTO.getDocumentTypeId()));

        Documents saved = documentRepository.save(documents);

        documentApproverService.createApproversForDocument(documents);

        return documentsMapper.convertEntityToDTO(saved);
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
