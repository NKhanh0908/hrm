package com.project.hrm.repositories;

import com.project.hrm.entities.PayrollComponents;
import com.project.hrm.enums.PayrollComponentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollComponentsRepository extends JpaRepository<PayrollComponents, Integer>, JpaSpecificationExecutor<PayrollComponents> {
    PayrollComponents findByPayrollIdAndType(Integer payrollId, PayrollComponentType type);
}
