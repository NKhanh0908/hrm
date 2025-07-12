package com.project.hrm.document.service;

import com.project.hrm.auth.entity.Account;

public interface PermissionService {
    boolean canApproveDocument(Account acc, Integer documentId);
    boolean canViewDocument(Account acc, Integer documentId);
    boolean canDownloadDocument(Account acc, Integer documentId);
    boolean canEditDocument(Account acc, Integer documentId);
    boolean canDeleteDocument(Account acc, Integer documentId);

}
