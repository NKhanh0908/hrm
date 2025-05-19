package com.project.hrm.repositories;

import com.project.hrm.dto.statisticsDTO.TotalEmployee;
import com.project.hrm.entities.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Departments, Integer>, JpaSpecificationExecutor<Departments> {

    @Modifying
    @Query(value = "UPDATE departments SET dean_id = :employeeID WHERE id = :departmentID ", nativeQuery = true)
    Departments updateDean(@Param("employeeID") Integer employeeID, @Param("departmentID") Integer departmentID);

    @Query(value =
            "SELECT " +
                    " d.id           AS department_id, " +
                    " d.department_name AS department_name, " +
                    " COUNT(e.id)    AS total " +
                    "FROM departments d " +
                    " JOIN contracts c ON d.id = c.department_id " +
                    " JOIN employees e ON c.employee_id = e.id " +
                    "WHERE d.id = :departmentId " +
                    "GROUP BY d.id, d.department_name",
            nativeQuery = true)
    List<TotalEmployee> getTotalEmployee(@Param("departmentId") Integer departmentId);
}
