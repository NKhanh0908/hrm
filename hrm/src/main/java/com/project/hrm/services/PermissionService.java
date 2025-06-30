package com.project.hrm.services;

import com.project.hrm.entities.Account;
import com.project.hrm.enums.DocumentAccess;

import java.util.Collection;

public interface PermissionService {
    boolean canApproveDocument(Account acc, Integer documentId);
    boolean canViewDocument(Account acc, Integer documentId);
    boolean canDownloadDocument(Account acc, Integer documentId);
    boolean canEditDocument(Account acc, Integer documentId);
    boolean canDeleteDocument(Account acc, Integer documentId);

}
