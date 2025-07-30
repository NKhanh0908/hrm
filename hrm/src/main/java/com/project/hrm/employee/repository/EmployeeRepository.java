package com.project.hrm.employee.repository;

import com.project.hrm.statistics.dto.TotalEmployeeByDepartment;
import com.project.hrm.statistics.dto.TotalEmployeeByDepartmentAndRole;
import com.project.hrm.statistics.dto.TotalEmployeeByRole;
import com.project.hrm.employee.entity.Employees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer>, JpaSpecificationExecutor<Employees> {

    @Query(value = "SELECT * FROM employees e WHERE e.id = :employeeId AND status = 'ACTIVE'", nativeQuery = true)
    Employees findEmployeeIsActive(@Param("employeeId") Integer id);

    @Query(value = "CALL get_total_employee_by_department()", nativeQuery = true)
    List<TotalEmployeeByDepartment> getTotalEmployeeByDepartment();

    @Query(value = "CALL get_total_employee_by_role()", nativeQuery = true)
    List<TotalEmployeeByRole> getTotalEmployeeByRole();

    @Query(value = "CALL get_total_employee_by_department_and_role()", nativeQuery = true)
    List<TotalEmployeeByDepartmentAndRole> getTotalEmployeeByDepartmentAndRole();

    @Query(value = """
            SELECT e.*
                FROM contracts c
                    INNER JOIN employees e ON c.employee_id = e.id
                    INNER JOIN role r ON c.role_id = r.id
                WHERE r.departments_id = :departmentId""", nativeQuery = true)
    List<Employees> findByDepartmentId(@Param("departmentId") Integer departmentId);

    @Query("SELECT e.id as employeeId, e as Employee " +
            "FROM Employees e WHERE e.id in :employeeIds")
    List<Object[]> getBatchEmployeeForPayPeriod(@Param("employeeIds") List<Integer> employeeIds);

    @Query("SELECT id as employeeId " +
            "FROM Employees WHERE status = 'ACTIVE'")
    List<Integer> getAllActiveEmployeeIds();

    @Query(
            value = """
        SELECT e.* FROM employees e
        JOIN account a ON a.employees_id = e.id
        JOIN role r ON e.role_id = r.id
        JOIN departments d ON r.departments_id = d.id
        WHERE d.id = :departmentId
          AND a.role IN (:roles)
    """,
            nativeQuery = true
    )
    List<Employees> findApproversByDepartmentAndRoles(
            @Param("departmentId") Integer departmentId,
            @Param("roles") List<String> roles
    );

    @Query(
            value = """
        SELECT e.* FROM employees e
        JOIN account a ON a.employees_id = e.id
        WHERE a.role IN (:roles)
          AND e.status IN ('ACTIVE', 'INACTIVE', 'PROBATION')
    """,
            nativeQuery = true
    )
    List<Employees> findByRoleAndAllowedStatus_Native(
            @Param("roles") List<String> roles
    );


}
