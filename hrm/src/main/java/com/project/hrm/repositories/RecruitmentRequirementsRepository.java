package com.project.hrm.repositories;

import com.project.hrm.entities.RecruitmentRequirements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRequirementsRepository extends JpaRepository<RecruitmentRequirements, Integer>, JpaSpecificationExecutor<RecruitmentRequirements> {
    @Modifying
    @Query(value =
            "UPDATE recruitment_requirements " +
                    "SET status = :status " +
                    "WHERE id = :id",
            nativeQuery = true)
    int updateStatus(
            @Param("id") Integer id,
            @Param("status") String status
    );
}
