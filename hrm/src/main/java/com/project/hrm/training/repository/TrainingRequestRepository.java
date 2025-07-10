package com.project.hrm.training.repository;

import com.project.hrm.training.entity.TrainingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRequestRepository extends JpaRepository<TrainingRequest, Integer>, JpaSpecificationExecutor<TrainingRequest> {
}
