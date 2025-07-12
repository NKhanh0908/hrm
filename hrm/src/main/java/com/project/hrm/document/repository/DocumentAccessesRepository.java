package com.project.hrm.document.repository;

import com.project.hrm.document.entity.DocumentAccesses;
import com.project.hrm.document.enums.DocumentAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;

public interface DocumentAccessesRepository extends JpaRepository<DocumentAccesses,Integer>, JpaSpecificationExecutor<DocumentAccesses> {
    boolean existsByDocumentsIdAndEmployeesIdAndAccessLevelIn(Integer documentId, Integer employeeId, Collection<DocumentAccess> accessLevels);
}
