package com.project.hrm.repositories;

import com.project.hrm.entities.DocumentAccesses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentAccessesRepository extends JpaRepository<DocumentAccesses,Integer>, JpaSpecificationExecutor<DocumentAccesses> {
}
