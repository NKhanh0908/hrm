package com.project.hrm.repositories;

import com.project.hrm.entities.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Integer>, JpaSpecificationExecutor<PerformanceReview> {
}
