package com.project.hrm.repositories;

import com.project.hrm.entities.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer>, JpaSpecificationExecutor<Recruitment> {

    @Modifying
    @Query(value =
            "UPDATE recruitment " +
                    "SET status = :status " +
                    "WHERE id = :id ",
            nativeQuery = true)
    int updateStatus(
            @Param("id") Integer id,
            @Param("status") String status
    );
}
