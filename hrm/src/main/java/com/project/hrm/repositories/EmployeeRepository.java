package com.project.hrm.repositories;

import com.project.hrm.dto.statisticsDTO.TotalEmployeeByDepartment;
import com.project.hrm.dto.statisticsDTO.TotalEmployeeByDepartmentAndRole;
import com.project.hrm.dto.statisticsDTO.TotalEmployeeByRole;
import com.project.hrm.entities.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer>, JpaSpecificationExecutor<Employees> {
    @Query(value = "SELECT d.id, d.department_name, count(*) as total FROM employees e INNER JOIN contracts c on e.id = c.employee_id INNER JOIN departments d on c.departments_id = d.id\n" +
            "GROUP BY d.id", nativeQuery = true)
    List<TotalEmployeeByDepartment> getTotalEmployeeByDepartment();

    @Query(value = "SELECT r.id, r.name, count(*) as total FROM employees e INNER JOIN contracts c on e.id = c.employee_id INNER JOIN role r on c.role_id = r.id\n" +
            "GROUP BY r.id", nativeQuery = true)
    List<TotalEmployeeByRole> getTotalEmployeeByRole();

    @Query(value = "SELECT r.id,  r.name, d.id, d.department_name, count(*) as total FROM employees e INNER JOIN contracts c on e.id = c.employee_id INNER JOIN departments d on c.departments_id = d.id INNER JOIN role r on c.role_id = r.id\n" +
            "GROUP BY r.id, d.id, d.department_name", nativeQuery = true)
    List<TotalEmployeeByDepartmentAndRole> getTotalEmployeeByDepartmentAndRole();
}
