package com.project.hrm.repositories;

import com.project.hrm.entities.PayPeriods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PayPeriodsRepository extends JpaRepository<PayPeriods, Integer>, JpaSpecificationExecutor<PayPeriods> {
    @Query("SELECT p FROM PayPeriods p WHERE p.startDate <= :startDate AND p.endDate >= :endDate")
    Optional<PayPeriods> getByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
