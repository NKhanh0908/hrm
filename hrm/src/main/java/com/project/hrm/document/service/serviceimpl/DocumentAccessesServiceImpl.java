package com.project.hrm.document.service.serviceimpl;

import com.project.hrm.document.dto.documentAccessesDTO.DocumentAccessesCreateDTO;
import com.project.hrm.document.dto.documentAccessesDTO.DocumentAccessesDTO;
import com.project.hrm.document.dto.documentAccessesDTO.DocumentAccessesUpdateDTO;
import com.project.hrm.document.entity.DocumentAccesses;
import com.project.hrm.document.enums.DocumentAccess;
import com.project.hrm.document.mapper.DocumentAccessesMapper;
import com.project.hrm.document.repository.DocumentAccessesRepository;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.document.service.DocumentAccessesService;
import com.project.hrm.document.service.DocumentsService;
import com.project.hrm.document.specification.DocumentAccessesSpecification;
import com.project.hrm.common.utils.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentAccessesServiceImpl implements DocumentAccessesService {
    private final DocumentAccessesRepository documentAccessesRepository;
    private final DocumentAccessesMapper documentAccessesMapper;
    private final AccountService accountService;
    private final DocumentsService documentService;

    @Transactional
    @Override
    public DocumentAccessesDTO create(DocumentAccessesCreateDTO documentAccessesCreateDTO) {
        DocumentAccesses documentAccesses = documentAccessesMapper.covertCreateDTOToEntity(documentAccessesCreateDTO);
        documentAccesses.setId(IdGenerator.getGenerationId());
        documentAccesses.setDocuments(documentService.getEntityById(documentAccessesCreateDTO.getDocumentId()));
        documentAccesses.setEmployees(accountService.getPrincipal());

        return documentAccessesMapper.covertEntityToDTO(documentAccessesRepository.save(documentAccesses));
    }

    @Transactional(readOnly = true)
    @Override
    public DocumentAccessesDTO getDTOById(Integer id) {
        return documentAccessesMapper.covertEntityToDTO(getEntityById(id));
    }

    @Transactional
    @Override
    public DocumentAccessesDTO update(DocumentAccessesUpdateDTO documentAccessesUpdateDTO) {
        DocumentAccesses documentAccesses = getEntityById(documentAccessesUpdateDTO.getId());
        if (documentAccessesUpdateDTO.getAccessLevel() != null) {
            documentAccesses.setAccessLevel(DocumentAccess.valueOf(documentAccessesUpdateDTO.getAccessLevel()));
        }
        documentAccesses.setDocuments(documentService.getEntityById(documentAccessesUpdateDTO.getDocumentId()));
        documentAccesses.setEmployees(accountService.getPrincipal());
        return documentAccessesMapper.covertEntityToDTO(documentAccessesRepository.save(documentAccesses));
    }

    @Transactional(readOnly = true)
    @Override
    public DocumentAccesses getEntityById(Integer id) {
        return documentAccessesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document Access not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<DocumentAccessesDTO> filterByDocumentId(Integer documentId, String accessLevel, String employeeName, int page, int size) {
        Specification<DocumentAccesses> spec = DocumentAccessesSpecification.filter(documentId, accessLevel, employeeName);
        Pageable pageable = PageRequest.of(page, size);
        return documentAccessesMapper.convertPageToListDTO(documentAccessesRepository.findAll(spec, pageable).getContent());
    }
}
