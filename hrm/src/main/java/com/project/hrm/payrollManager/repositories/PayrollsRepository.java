package com.project.hrm.payrollManager.repositories;

import com.project.hrm.payrollManager.entities.Payrolls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollsRepository extends JpaRepository<Payrolls, Integer>, JpaSpecificationExecutor<Payrolls> {
}
