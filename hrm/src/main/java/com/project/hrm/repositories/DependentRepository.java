package com.project.hrm.repositories;

import com.project.hrm.entities.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependentRepository extends JpaRepository<Dependent, Integer> {
}
