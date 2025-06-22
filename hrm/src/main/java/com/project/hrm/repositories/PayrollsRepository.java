package com.project.hrm.repositories;

import com.project.hrm.entities.Payrolls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollsRepository extends JpaRepository<Payrolls, Integer> {
}
