package com.project.hrm.repositories;

import com.project.hrm.entities.PerformanceReviewDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceReviewDetailRepository extends JpaRepository<PerformanceReviewDetail, Integer>, JpaSpecificationExecutor<PerformanceReviewDetail> {
}
