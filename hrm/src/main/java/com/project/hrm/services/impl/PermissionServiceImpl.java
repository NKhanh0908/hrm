package com.project.hrm.services.impl;

import com.project.hrm.entities.Account;
import com.project.hrm.entities.Documents;
import com.project.hrm.enums.AccountRole;
import com.project.hrm.enums.DocumentAccess;
import com.project.hrm.repositories.DocumentAccessesRepository;
import com.project.hrm.repositories.DocumentRepository;
import com.project.hrm.services.AccountService;
import com.project.hrm.services.DocumentsService;
import com.project.hrm.services.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Component("permissionService")
public class PermissionServiceImpl implements PermissionService {
    private final DocumentAccessesRepository documentAccessesRepository;
    private final DocumentsService documentsService;

    private boolean hasRole(Account acc, AccountRole role) {
        return acc.getRole() == role;
    }


    private boolean hasAccess(Integer empId, Integer documentId, Collection<DocumentAccess> levels) {
        boolean isOwner = documentAccessesRepository.existsByDocumentsIdAndEmployeesIdAndAccessLevelIn(documentId, empId, List.of(DocumentAccess.FULL_ACCESS));
        if (isOwner) {
            return true;
        }

        return documentAccessesRepository.existsByDocumentsIdAndEmployeesIdAndAccessLevelIn(documentId, empId, levels);
    }

    @Override
    public boolean canApproveDocument(Account acc, Integer documentId) {
        return false;
    }

    @Override
    public boolean canViewDocument(Account acc, Integer documentId) {
        if(hasRole(acc, AccountRole.ADMIN) || hasRole(acc, AccountRole.HR)) {
            return true;
        }
        Documents doc = documentsService.getEntityById(documentId);
        Integer empId = acc.getEmployees().getId();
        if (doc.getUploadedBy().getId().equals(empId)) {
            return true;
        }
        return hasAccess(empId, documentId, List.of(DocumentAccess.VIEW, DocumentAccess.DOWNLOAD));
    }

    @Override
    public boolean canDownloadDocument(Account acc, Integer documentId) {
        return false;
    }

    @Override
    public boolean canEditDocument(Account acc, Integer documentId) {
        return false;
    }

    @Override
    public boolean canDeleteDocument(Account acc, Integer documentId) {
        return false;
    }
}
