package com.project.hrm.repositories;

import com.project.hrm.dto.statisticsDTO.TotalEmployeeByDepartment;
import com.project.hrm.dto.statisticsDTO.TotalEmployeeByDepartmentAndRole;
import com.project.hrm.dto.statisticsDTO.TotalEmployeeByRole;
import com.project.hrm.entities.Employees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
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
                WHERE r.departments_id = :departmentId""", countQuery = """
        SELECT COUNT(*)
        FROM contracts c
        INNER JOIN employees e ON c.employee_id = e.id
        INNER JOIN role r ON c.role_id = r.id
        WHERE r.departments_id = :departmentId""", nativeQuery = true)
    Page<Employees> findByDepartmentId(@Param("departmentId") Integer departmentId, Pageable pageable);

    @Query("SELECT e.id as employeeId, e as Employee " +
            "FROM Employees e WHERE e.id in :employeeIds")
    List<Object[]> getBatchEmployeeForPayPeriod(@Param("employeeIds") List<Integer> employeeIds);

    @Query("SELECT id as employeeId " +
            "FROM Employees WHERE status = 'ACTIVE'")
    List<Integer> getAllActiveEmployeeIds();
}
