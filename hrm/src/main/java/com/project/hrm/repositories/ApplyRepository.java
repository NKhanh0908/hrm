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

    @Query(value = "CALL getInfoApply(:id);", nativeQuery = true)
    InfoApply getInfoApply(@Param("id") Integer id);

    @Query(value = "CALL get_role_id_by_apply_id(:applyId)", nativeQuery = true)
    Integer getRoleIdByApplyId(@Param("applyId") Integer applyId);
}
