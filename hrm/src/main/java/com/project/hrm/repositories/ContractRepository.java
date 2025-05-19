package com.project.hrm.repositories;

import com.project.hrm.entities.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contracts, Integer>, JpaSpecificationExecutor<Contracts> {
}
