package com.project.hrm.repositories;

import com.project.hrm.enums.SystemRegulationKey;
import com.project.hrm.entities.SystemRegulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRegulationRepository extends JpaRepository<SystemRegulation, SystemRegulationKey> {
}
