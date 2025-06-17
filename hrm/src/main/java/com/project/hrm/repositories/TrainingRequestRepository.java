package com.project.hrm.repositories;

import com.project.hrm.entities.TrainingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRequestRepository extends JpaRepository<TrainingRequest, Integer>, JpaSpecificationExecutor<TrainingRequest> {
}
