package com.project.hrm.department.repository;

import com.project.hrm.department.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Departments, Integer>, JpaSpecificationExecutor<Departments> {
    @Query(value = "CALL get_departments_by_employee_id(:employeeId)", nativeQuery = true)
    Departments getDepartmentsByEmployeeId(@Param("employeeId") Integer employeeId);
}
