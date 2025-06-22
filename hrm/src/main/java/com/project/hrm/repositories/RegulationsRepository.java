package com.project.hrm.repositories;

import com.project.hrm.entities.Regulations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RegulationsRepository extends JpaRepository<Regulations, Integer>, JpaSpecificationExecutor<Regulations> {
}
