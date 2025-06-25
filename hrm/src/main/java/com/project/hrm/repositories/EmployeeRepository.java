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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer>, JpaSpecificationExecutor<Employees> {

    @Query(value = "SELECT * FROM employees e WHERE e.id = :employeeId AND status = 'ACTIVE'", nativeQuery = true)
    Employees findEmployeeIsActive(@Param("employeeId") Integer id);

    @Query(value = """
            SELECT d.id, d.department_name, count(*) as total
            FROM employees e
                INNER JOIN contracts c on e.id = c.employee_id
                INNER JOIN role r on c.role_id = r.id
                INNER JOIN departments d on r.departments_id = d.id
                        GROUP BY d.id""", nativeQuery = true)
    List<TotalEmployeeByDepartment> getTotalEmployeeByDepartment();

    @Query(value = """
            SELECT r.id, r.name, count(*) as total
            FROM employees e
                INNER JOIN contracts c on e.id = c.employee_id
                INNER JOIN role r on c.role_id = r.id
                    GROUP BY r.id""", nativeQuery = true)
    List<TotalEmployeeByRole> getTotalEmployeeByRole();

    @Query(value = """
            SELECT r.id as role_id,  r.name as role_name, d.id as department_id, d.department_name, count(*) as total
                FROM employees e
                    INNER JOIN contracts c on e.id = c.employee_id
                    INNER JOIN role r on c.role_id = r.id
                    INNER JOIN departments d on r.departments_id = d.id
                        GROUP BY r.id, d.id, d.department_name""", nativeQuery = true)
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
}
