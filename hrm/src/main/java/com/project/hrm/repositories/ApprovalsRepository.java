package com.project.hrm.repositories;

import com.project.hrm.entities.Approvals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalsRepository extends JpaRepository<Approvals, Integer> {
}
