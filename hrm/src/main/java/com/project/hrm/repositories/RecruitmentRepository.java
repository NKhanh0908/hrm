package com.project.hrm.repositories;

import com.project.hrm.entities.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer>, JpaSpecificationExecutor<Recruitment> {
}
