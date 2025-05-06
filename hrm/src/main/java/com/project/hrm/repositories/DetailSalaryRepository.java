package com.project.hrm.repositories;

import com.project.hrm.entities.DetailSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailSalaryRepository extends JpaRepository<DetailSalary, Integer> {
}
