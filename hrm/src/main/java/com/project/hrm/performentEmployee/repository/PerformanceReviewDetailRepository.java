package com.project.hrm.performentEmployee.repository;

import com.project.hrm.performentEmployee.entity.PerformanceReviewDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceReviewDetailRepository extends JpaRepository<PerformanceReviewDetail, Integer>, JpaSpecificationExecutor<PerformanceReviewDetail> {
}
