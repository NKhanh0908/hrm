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
        Documents doc = documentsService.getEntityById(documentId);
        Integer empId = acc.getEmployees().getId();
        if (hasRole(acc, AccountRole.ADMIN) || hasRole(acc, AccountRole.HR)) {
            return true;
        }
        if (doc.getUploadedBy().getId().equals(empId)) {
            return true;
        }
        switch (doc.getDocumentScope()) {
            case PERSONAL:
                return false;

            case DEPARTMENT:
                Integer uploaderDeptId = doc.getUploadedBy().getRole().getDepartments().getId();
                Integer userDeptId = acc.getEmployees().getRole().getDepartments().getId();
                if (!uploaderDeptId.equals(userDeptId)) return false;
                return hasAccess(empId, documentId, List.of(DocumentAccess.APPROVE, DocumentAccess.FULL_ACCESS));

            case COMPANY:
                return hasAccess(empId, documentId, List.of(DocumentAccess.APPROVE, DocumentAccess.FULL_ACCESS));

            case RESTRICTED:
                return hasAccess(empId, documentId, List.of(DocumentAccess.APPROVE, DocumentAccess.FULL_ACCESS));
        }
        return hasAccess(empId, documentId, List.of(DocumentAccess.APPROVE, DocumentAccess.FULL_ACCESS));
    }

    @Override
    public boolean canViewDocument(Account acc, Integer documentId) {
        Documents doc = documentsService.getEntityById(documentId);
        Integer empId = acc.getEmployees().getId();
        Integer userDeptId = acc.getEmployees().getRole().getDepartments().getId();

        if(hasRole(acc, AccountRole.ADMIN) || hasRole(acc, AccountRole.HR)) {
            return true;
        }

        if (doc.getUploadedBy().getId().equals(empId)) {
            return true;
        }

        switch (doc.getDocumentScope()) {
            case PERSONAL:
                return false;

            case DEPARTMENT:

                Integer uploaderDeptId = doc.getUploadedBy().getRole().getDepartments().getId();
                if (!uploaderDeptId.equals(userDeptId)) return false;
                return hasAccess(empId, documentId, List.of(DocumentAccess.VIEW, DocumentAccess.DOWNLOAD, DocumentAccess.FULL_ACCESS));

            case COMPANY:
                return hasAccess(empId, documentId, List.of(DocumentAccess.VIEW, DocumentAccess.DOWNLOAD, DocumentAccess.FULL_ACCESS));

            case RESTRICTED:
                return hasAccess(empId, documentId, List.of(DocumentAccess.VIEW, DocumentAccess.DOWNLOAD, DocumentAccess.FULL_ACCESS));
        }
        return hasAccess(empId, documentId, List.of(DocumentAccess.VIEW, DocumentAccess.DOWNLOAD));
    }

    @Override
    public boolean canDownloadDocument(Account acc, Integer documentId) {
        Documents doc = documentsService.getEntityById(documentId);
        Integer empId = acc.getEmployees().getId();
        Integer userDeptId = acc.getEmployees().getRole().getDepartments().getId();

        if(hasRole(acc, AccountRole.ADMIN) || hasRole(acc, AccountRole.HR)) {
            return true;
        }
        if (doc.getUploadedBy().getId().equals(empId)) {
            return true;
        }

        switch (doc.getDocumentScope()) {
            case PERSONAL:
                return false;
            case DEPARTMENT:
                Integer uploaderDeptId = doc.getUploadedBy().getRole().getDepartments().getId();
                if (!uploaderDeptId.equals(userDeptId)) return false;
                return hasAccess(empId, documentId, List.of(DocumentAccess.DOWNLOAD, DocumentAccess.FULL_ACCESS));
            case COMPANY:
                return hasAccess(empId, documentId, List.of(DocumentAccess.DOWNLOAD, DocumentAccess.FULL_ACCESS));
            case RESTRICTED:
                return hasAccess(empId, documentId, List.of(DocumentAccess.DOWNLOAD, DocumentAccess.FULL_ACCESS));
        }
        return hasAccess(empId, documentId, List.of(DocumentAccess.DOWNLOAD));
    }

    @Override
    public boolean canEditDocument(Account acc, Integer documentId) {
        return false;
    }

    @Override
    public boolean canDeleteDocument(Account acc, Integer documentId) {
        Documents doc = documentsService.getEntityById(documentId);
        Integer empId = acc.getEmployees().getId();
        if (hasRole(acc, AccountRole.ADMIN) || hasRole(acc, AccountRole.HR)) {
            return true;
        }
        if (doc.getUploadedBy().getId().equals(empId)) {
            return true;
        }

        switch (doc.getDocumentScope()) {
            case PERSONAL:
                return false;
            case DEPARTMENT:
                Integer uploaderDeptId = doc.getUploadedBy().getRole().getDepartments().getId();
                Integer userDeptId = acc.getEmployees().getRole().getDepartments().getId();
                if (!uploaderDeptId.equals(userDeptId)) return false;
                return hasAccess(empId, documentId, List.of(DocumentAccess.FULL_ACCESS));
            case COMPANY:
                return hasAccess(empId, documentId, List.of(DocumentAccess.FULL_ACCESS));
            case RESTRICTED:
                return hasAccess(empId, documentId, List.of(DocumentAccess.FULL_ACCESS));
    }
        return hasAccess(empId, documentId, List.of(DocumentAccess.FULL_ACCESS));
    }
}
