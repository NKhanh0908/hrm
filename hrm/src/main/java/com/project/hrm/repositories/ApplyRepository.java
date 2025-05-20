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
            SELECT cp.name, cp.email, a.position as position_apply
            FROM apply a
                inner join candidate_profile cp
                    on a.candicate_profile_id = cp.id
                inner join recruitment rm
                    on a.recruitment_id = rm.id
            WHERE a.id = :id
        """, nativeQuery = true)
    InfoApply getInfoApply(@Param("id") Integer id);
}
