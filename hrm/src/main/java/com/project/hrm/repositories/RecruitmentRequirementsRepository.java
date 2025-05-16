package com.project.hrm.repositories;

import com.project.hrm.entities.RecruitmentRequirements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRequirementsRepository extends JpaRepository<RecruitmentRequirements, Integer>, JpaSpecificationExecutor<RecruitmentRequirements> {
}
