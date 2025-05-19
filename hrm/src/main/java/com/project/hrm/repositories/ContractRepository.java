package com.project.hrm.repositories;

import com.project.hrm.entities.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ContractRepository extends JpaRepository<Contracts, Integer>, JpaSpecificationExecutor<Contracts> {
    @Query("""
          select case when count(c) > 0 then true else false end
          from Contracts c
          where c.employee.id = :employeeId
            and c.role.id = :roleId
            and c.startDate <= coalesce(:newEnd, c.endDate)
            and (c.endDate is null or c.endDate >= :newStart)
        """)
    boolean existsOverlappingContract(
            @Param("employeeId") Integer employeeId,
            @Param("roleId")       Integer roleId,
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd
    );

    @Modifying
    @Query(
            value = "UPDATE contracts " +
                    "SET contract_status = :status " +
                    "WHERE id = :id",
            nativeQuery = true
    )
    int updateStatus(
            @Param("id") Integer id,
            @Param("status") String status
    );

}
