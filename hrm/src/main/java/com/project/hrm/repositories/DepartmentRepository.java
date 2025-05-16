package com.project.hrm.repositories;

import com.project.hrm.entities.Departments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Departments, Integer> {
    Page<Departments> findAll(Specification<Departments> departmentsSpecification, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE departments SET dean_id = :employeeID WHERE id = :departmentID ", nativeQuery = true)
    Departments updateDean(@Param("employeeID") Integer employeeID, @Param("departmentID") Integer departmentID);
}
