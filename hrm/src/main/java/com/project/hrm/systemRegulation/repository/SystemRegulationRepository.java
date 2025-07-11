package com.project.hrm.systemRegulation.repository;

import com.project.hrm.systemRegulation.enums.SystemRegulationKey;
import com.project.hrm.systemRegulation.entity.SystemRegulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRegulationRepository extends JpaRepository<SystemRegulation, SystemRegulationKey> {
}
