package com.project.hrm.recruitment.repository;

import com.project.hrm.recruitment.entity.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, Integer> , JpaSpecificationExecutor<Evaluate> {
}
