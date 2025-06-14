package com.project.hrm.repositories;

import com.project.hrm.entities.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Integer>, JpaSpecificationExecutor<TrainingProgram> {
}
