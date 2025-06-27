package com.project.hrm.repositories;

import com.project.hrm.entities.DocumentTypes;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface DocumentTypesRepository extends JpaRepository<DocumentTypes,Integer> {
    @Query(
            value = "SELECT EXISTS (SELECT 1 FROM document_types dt WHERE dt.name = :name  ",
            nativeQuery = true
    )
    boolean existsByName(@Param("name") String name);
}
