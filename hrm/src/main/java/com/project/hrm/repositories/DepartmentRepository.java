package com.project.hrm.repositories;

import com.project.hrm.entities.Departments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Departments, Integer> {
    Page<Departments> findAll(Specification<Departments> departmentsSpecification, Pageable pageable);
}
