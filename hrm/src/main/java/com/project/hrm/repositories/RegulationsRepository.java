package com.project.hrm.repositories;

import com.project.hrm.entities.Regulations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegulationsRepository extends JpaRepository<Regulations, Integer>, JpaSpecificationExecutor<Regulations> {

    @Query("SELECT r FROM Regulations r WHERE r.regulationKey = :key")
    Optional<Regulations> findRegulationByKey(@Param("key") String regulationKey);
}
