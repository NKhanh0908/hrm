package com.project.hrm.employee.repository;

import com.project.hrm.employee.entity.Dependent;
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

    @Query("SELECT d.employee.id as employeeId, COUNT(d) as dependentCount " +
            "FROM Dependent d WHERE d.employee.id IN :employeeIds " +
            "GROUP BY d.employee.id")
    List<Object[]> getBatchDependentCount(@Param("employeeIds") List<Integer> employeeIds);

}
