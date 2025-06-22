package com.project.hrm.repositories;

import com.project.hrm.entities.PayrollComponents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollComponentsRepository extends JpaRepository<PayrollComponents, Integer>, JpaSpecificationExecutor<PayrollComponents> {
}
