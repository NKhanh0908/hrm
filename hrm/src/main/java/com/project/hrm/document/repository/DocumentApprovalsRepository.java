package com.project.hrm.document.repository;

import com.project.hrm.document.entity.DocumentApprovals;
import com.project.hrm.document.enums.DocumentApprovalsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentApprovalsRepository extends JpaRepository<DocumentApprovals,Integer>, JpaSpecificationExecutor<DocumentApprovals> {
    @Query(
            value = "SELECT EXISTS (SELECT 1 FROM document_approvals da WHERE da.documents_id = :documentId AND da.requested_by_id = :requestedById)",
            nativeQuery = true
    )
    boolean existsByDocumentsIdAndRequestedById(@Param("documentId") Integer documentId, @Param("requestedById") Integer requestedById);

    List<DocumentApprovals> findByDocumentApprovalsStatus(DocumentApprovalsStatus status);
}
