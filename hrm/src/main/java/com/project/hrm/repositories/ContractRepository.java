package com.project.hrm.repositories;

import com.project.hrm.entities.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contracts, Integer> {
}
