package com.project.hrm.payroll.repositories;

import com.project.hrm.payroll.entities.Approvals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalsRepository extends JpaRepository<Approvals, Integer>, JpaSpecificationExecutor<Approvals> {
}
