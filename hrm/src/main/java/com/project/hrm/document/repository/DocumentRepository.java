package com.project.hrm.document.repository;

import com.project.hrm.document.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DocumentRepository extends JpaRepository<Documents,Integer> {
}
