package com.project.hrm.repositories;

import com.project.hrm.entities.PayrollComponents;
import com.project.hrm.enums.PayrollComponentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayrollComponentsRepository extends JpaRepository<PayrollComponents, Integer>, JpaSpecificationExecutor<PayrollComponents> {
    @Query("SELECT pc FROM PayrollComponents pc WHERE pc.payroll.id = :payrollId AND pc.type = :type")
    Optional<PayrollComponents> findByPayrollIdAndType(@Param("payrollId") Integer payrollId, @Param("type") PayrollComponentType type);
}
