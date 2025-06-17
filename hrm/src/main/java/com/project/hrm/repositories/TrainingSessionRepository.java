package com.project.hrm.repositories;

import com.project.hrm.entities.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Integer>, JpaSpecificationExecutor<TrainingSession> {
}
