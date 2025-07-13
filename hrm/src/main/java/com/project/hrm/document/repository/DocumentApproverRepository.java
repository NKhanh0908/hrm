package com.project.hrm.document.repository;

import com.project.hrm.document.entity.DocumentApprover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentApproverRepository extends JpaRepository<DocumentApprover, Integer> {
    List<DocumentApprover> findByDocumentId(Integer documentId);
}
