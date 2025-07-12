package com.project.hrm.performentEmployee.repository;

import com.project.hrm.performentEmployee.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Integer>, JpaSpecificationExecutor<PerformanceReview> {
}
