package com.project.hrm.repositories;

import com.project.hrm.entities.PayrollDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollDetailsRepository extends JpaRepository<PayrollDetails, Integer> {
}
