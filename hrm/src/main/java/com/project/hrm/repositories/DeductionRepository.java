package com.project.hrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hrm.entities.Deduction;

public interface DeductionRepository extends JpaRepository<Deduction, Integer>{
}
