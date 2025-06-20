package com.project.hrm.repositories;

import com.project.hrm.entities.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Integer>, JpaSpecificationExecutor<TrainingSession> {
    List<TrainingSession> findAllByTrainingProgramId(Integer id);
}
