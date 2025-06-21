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
    @Query(value = """
            SELECT d.*
            FROM departments d
                     JOIN role r ON r.departments_id = d.id
                     JOIN (
                SELECT c.role_id
                FROM contracts c
                WHERE c.employee_id = :employeeId
                  AND c.contract_status = 'ACTIVE'
                ORDER BY c.start_date DESC
                LIMIT 1
            ) latest_contract ON latest_contract.role_id = r.id""", nativeQuery = true)
    Departments getDepartmentsByEmployeeId(@Param("employeeId") Integer employeeId);
}
