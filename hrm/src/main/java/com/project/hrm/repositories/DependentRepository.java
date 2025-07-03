package com.project.hrm.repositories;

import com.project.hrm.entities.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependentRepository extends JpaRepository<Dependent, Integer>, JpaSpecificationExecutor<Dependent> {
    @Query("SELECT d FROM Dependent d WHERE d.employee.id = :employeeId")
    List<Dependent> findByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query("SELECT COUNT(d) FROM Dependent d WHERE d.employee.id = :employeeId")
    int countByEmployeeId(@Param("employeeId") Integer employeeId);
}
