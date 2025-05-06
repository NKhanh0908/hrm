package com.project.hrm.repositories;

import com.project.hrm.entities.Subsidy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsidyRepository extends JpaRepository<Subsidy, Integer> {
}
