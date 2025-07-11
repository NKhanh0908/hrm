package com.project.hrm.payroll.repositories;

import com.project.hrm.payroll.entities.Regulations;
import com.project.hrm.payroll.enums.PayrollComponentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegulationsRepository extends JpaRepository<Regulations, Integer>, JpaSpecificationExecutor<Regulations> {

    @Query("SELECT r FROM Regulations r WHERE r.regulationKey = :key")
    Optional<Regulations> findRegulationByKey(@Param("key") String regulationKey);

    List<Regulations> findByType(PayrollComponentType type);
}
