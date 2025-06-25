package com.project.hrm.repositories;

import com.project.hrm.entities.PayPeriods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PayPeriodsRepository extends JpaRepository<PayPeriods, Integer>, JpaSpecificationExecutor<PayPeriods> {
    PayPeriods getByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDateTime startDate, LocalDateTime endDate);
}
