package com.project.hrm.repositories;

import com.project.hrm.entities.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingEnrollmentRepository extends JpaRepository<TrainingEnrollment, Integer>, JpaSpecificationExecutor<TrainingEnrollment> {
}
