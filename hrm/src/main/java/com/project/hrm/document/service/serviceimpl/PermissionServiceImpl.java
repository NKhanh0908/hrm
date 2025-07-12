package com.project.hrm.document.service.serviceimpl;

import com.project.hrm.auth.entity.Account;
import com.project.hrm.document.entity.Documents;
import com.project.hrm.auth.enums.AccountRole;
import com.project.hrm.document.enums.DocumentAccess;
import com.project.hrm.document.repository.DocumentAccessesRepository;
import com.project.hrm.document.service.DocumentsService;
import com.project.hrm.document.service.PermissionService;
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
