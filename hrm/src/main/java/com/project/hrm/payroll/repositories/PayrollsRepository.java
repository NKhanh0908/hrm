package com.project.hrm.payroll.repositories;

import com.project.hrm.payroll.entities.Payrolls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollsRepository extends JpaRepository<Payrolls, Integer>, JpaSpecificationExecutor<Payrolls> {
}
