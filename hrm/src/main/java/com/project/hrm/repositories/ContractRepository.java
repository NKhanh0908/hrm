package com.project.hrm.repositories;

import com.project.hrm.dto.statisticsDTO.TotalContractByStatus;
import com.project.hrm.dto.statisticsDTO.TotalContractByStatusAndSalary;
import com.project.hrm.entities.Contracts;
import com.project.hrm.enums.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contracts, Integer>, JpaSpecificationExecutor<Contracts> {
    @Query(value = "CALL exists_overlapping_active_contract(:employee_id, :role_id, :new_start, :new_end)", nativeQuery = true)
    boolean existsOverlappingActiveContract(
            @Param("employee_id") Integer employeeId,
            @Param("role_id")     Integer roleId,
            @Param("new_start")   LocalDateTime newStart,
            @Param("new_end")     LocalDateTime newEnd);


    @Query("""
          select c from Contracts c
          where c.employee.id = :employeeId
            and c.contractStatus in ('ACTIVE', 'SIGNED')
          order by
            case when c.contractStatus = 'ACTIVE' then 1 else 2 end,
            c.startDate desc
          limit 1
        """)
    Optional<Contracts> findCurrentActiveContract(@Param("employeeId") Integer employeeId);

    @Query("""
          select c from Contracts c
          where c.employee.id = :employeeId
            and c.role.id = :roleId
            and c.contractStatus = 'ACTIVE'
          order by c.startDate desc
          limit 1
        """)
    Optional<Contracts> findCurrentActiveContractByRole(
            @Param("employeeId") Integer employeeId,
            @Param("roleId") Integer roleId
    );

    @Query("""
          select case when count(c) > 0 then true else false end
          from Contracts c
          where c.employee.id = :employeeId
            and c.contractStatus = 'ACTIVE'
        """)
    boolean hasActiveContract(@Param("employeeId") Integer employeeId);

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

    @Query(value = """
            SELECT contract_status AS status,
                COUNT(*) AS total
                    FROM contracts
                    GROUP BY contract_status
            """, nativeQuery = true)
    List<TotalContractByStatus> getTotalContractByStatus();

    @Query(value = "CALL get_total_contract_by_status_and_salary(:contractStatus)", nativeQuery = true)
    List<TotalContractByStatusAndSalary> getTotalContractByStatusAndSalary(@Param("contractStatus") String contractStatus);

    @Modifying
    @Query("""
           UPDATE Contracts c
           SET c.contractStatus = 'ACTIVE'
           WHERE c.startDate <= :currentDate
             AND c.contractStatus = 'SIGNED'
             AND c.endDate >= :currentDate
           """)
    int activateSignedContracts(@Param("currentDate") LocalDateTime currentDate);

    @Modifying
    @Query("""
           UPDATE Contracts c
           SET c.contractStatus = 'SUSPENDED'
           WHERE c.endDate BETWEEN :currentDate AND :futureDate
             AND c.contractStatus = 'ACTIVE'
           """)
    int expireActiveContracts(@Param("currentDate") LocalDateTime currentDate);

    @Modifying
    @Query("""
           UPDATE Contracts c
           SET c.contractStatus = 'SUSPENDED'
           WHERE c.endDate BETWEEN :currentDate AND :futureDate
             AND c.contractStatus = 'ACTIVE'
           """)
    int suspendExpiringContracts(
            @Param("currentDate") LocalDateTime currentDate,
            @Param("futureDate") LocalDateTime futureDate
    );
}
