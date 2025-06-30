package com.project.hrm.repositories;

import com.project.hrm.entities.DocumentAccesses;
import com.project.hrm.enums.DocumentAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;

public interface DocumentAccessesRepository extends JpaRepository<DocumentAccesses,Integer>, JpaSpecificationExecutor<DocumentAccesses> {
    boolean existsByDocumentsIdAndEmployeesIdAndAccessLevelIn(Integer documentId, Integer employeeId, Collection<DocumentAccess> accessLevels);
}
