package com.project.hrm.training.repository;

import com.project.hrm.training.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Integer>, JpaSpecificationExecutor<TrainingSession> {
    List<TrainingSession> findAllByTrainingProgramId(Integer id);
}
