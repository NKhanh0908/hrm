package com.project.hrm.repositories;

import com.project.hrm.entities.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Departments, Integer>, JpaSpecificationExecutor<Departments> {
    @Query(value = "CALL get_departments_by_employee_id(:employeeId)", nativeQuery = true)
    Departments getDepartmentsByEmployeeId(@Param("employeeId") Integer employeeId);
}
