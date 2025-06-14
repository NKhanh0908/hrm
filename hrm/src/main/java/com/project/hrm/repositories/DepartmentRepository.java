package com.project.hrm.repositories;

import com.project.hrm.entities.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository
                extends JpaRepository<Departments, Integer>, JpaSpecificationExecutor<Departments> {

        @Modifying
        @Query(value = "UPDATE departments SET dean_id = :employeeID WHERE id = :departmentID ", nativeQuery = true)
        Departments updateDean(@Param("employeeID") Integer employeeID, @Param("departmentID") Integer departmentID);

}
