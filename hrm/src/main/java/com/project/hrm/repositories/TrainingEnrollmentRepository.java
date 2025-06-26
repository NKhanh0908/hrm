package com.project.hrm.repositories;

import com.project.hrm.entities.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingEnrollmentRepository extends JpaRepository<TrainingEnrollment, Integer>, JpaSpecificationExecutor<TrainingEnrollment> {

    @Query(value = """
        SELECT EXISTS (
            SELECT 1
            FROM training_enrollment
            WHERE training_session_id  = :trainingSessionId
              AND training_request_id = :trainingRequestId
              AND status IN ('ENROLLED', 'IN_PROGRESS')
        )
        """, nativeQuery = true)
    boolean existsActiveEnrollment(@Param("trainingSessionId") Integer trainingSessionId,
                                   @Param("trainingRequestId") Integer trainingRequestId);
}
