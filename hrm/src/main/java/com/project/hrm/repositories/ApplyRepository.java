package com.project.hrm.repositories;

import com.project.hrm.dto.othersDTO.InfoApply;
import com.project.hrm.entities.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Integer>, JpaSpecificationExecutor<Apply> {
    @Modifying
    @Query(value =
            "UPDATE apply " +
                    "SET apply_status = :status " +
                    "WHERE id = :id",
            nativeQuery = true)
    int updateStatus(
            @Param("id") Integer id,
            @Param("status") String status
    );

    @Transactional(readOnly = true)
    @Query(value = """
            SELECT cp.name, cp.email, rl.name as position_apply
                        FROM (
                            SELECT candidate_profile_id, recruitment_id
                            FROM apply
                            WHERE id = :id
                        ) a
                            INNER JOIN candidate_profile cp
                                ON a.candidate_profile_id = cp.id
                            INNER JOIN recruitment rm
                                ON a.recruitment_id = rm.id
                        INNER JOIN recruitment_requirements rr ON rm.recruitment_requirements_id = rr.id
                        INNER JOIN role rl ON rr.role_id = rl.id
        """, nativeQuery = true)
    InfoApply getInfoApply(@Param("id") Integer id);

    @Query(value = """
            SELECT rl.id
FROM (
         SELECT recruitment_id
         FROM apply
         WHERE id = :applyId
     ) a
            INNER JOIN recruitment r ON a.recruitment_id = r.id
    INNER JOIN recruitment_requirements rr ON r.recruitment_requirements_id = rr.id
    INNER JOIN role rl ON rr.role_id = rl.id
            """, nativeQuery = true)
    Integer getRoleIdByApplyId(@Param("applyId") Integer applyId);
}
