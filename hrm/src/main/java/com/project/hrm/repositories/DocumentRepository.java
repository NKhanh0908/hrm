package com.project.hrm.repositories;

import com.project.hrm.entities.Documents;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DocumentRepository extends JpaRepository<Documents,Integer> {
}
